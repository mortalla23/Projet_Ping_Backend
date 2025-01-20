const BASE_URL = 'http://localhost:5000/api';
let webSocket = null;

// 🔐 Fonction de connexion de l'utilisateur
async function loginUser(email, password) {
    try {
        const response = await fetch(`${BASE_URL}/users/connexion`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, password })
        });

        const result = await response.json();

        if (!response.ok) {
            console.error("Réponse de l'API :", result);
            throw new Error(result.message || 'Échec de la connexion');
        }

        console.log("Connexion réussie :", result);

        // ✅ Stocker l'ID utilisateur dans le stockage local
        localStorage.setItem('userId', result.id);

        // 🚀 Redirection vers la page de messagerie
        window.location.href = "chat.html";

    } catch (error) {
        console.error("Erreur lors de la connexion :", error);
        alert("Erreur lors de la connexion. Veuillez vérifier vos identifiants.");
    }
}

// 📥 Récupérer les conversations de l'utilisateur et s'abonner
async function fetchUserConversations(userId) {
    try {
        const response = await fetch(`${BASE_URL}/conversations/user/${userId}`);

        if (!response.ok) {
            throw new Error('Erreur lors de la récupération des conversations');
        }

        const conversations = await response.json();
        console.log("Conversations récupérées :", conversations);

        // 📡 Connexion au WebSocket
        connectWebSocket(conversations);

    } catch (error) {
        console.error("Erreur lors de la récupération des conversations :", error);
    }
}

// 📡 Connexion au WebSocket et abonnement aux conversations
function connectWebSocket(conversations) {
    webSocket = new WebSocket('ws://localhost:5000/ws');

    webSocket.onopen = () => {
        console.log("✅ Connecté au WebSocket");

        conversations.forEach(conversation => {
            const subscribeMessage = {
                action: "subscribe",
                conversation_id: conversation.id
            };
            webSocket.send(JSON.stringify(subscribeMessage));
            console.log(`📢 Abonné à la conversation ${conversation.id}`);
        });
    };

    webSocket.onmessage = (event) => {
        const data = JSON.parse(event.data);
        console.log("📥 Nouveau message reçu :", data);

        if (data.type === "newMessage") {
            displayMessage(data.content, data.conversationId);
        }
    };
}

// 💬 Afficher un message
function displayMessage(message, conversationId) {
    const conversationDiv = document.getElementById("conversations");
    const messageElement = document.createElement("p");
    messageElement.textContent = `Conv. ${conversationId}: ${message}`;
    conversationDiv.appendChild(messageElement);
}

// ✉️ Envoyer un message
async function sendMessage(conversationId, userId, content) {
    try {
        const response = await fetch(`${BASE_URL}/messages/add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                conversationId: conversationId,
                userId: userId,
                content: content
            })
        });

        if (!response.ok) {
            throw new Error("Erreur lors de l'envoi du message.");
        }

        console.log("📤 Message envoyé !");
    } catch (error) {
        console.error("Erreur lors de l'envoi :", error);
    }
}

// 📤 Gestionnaire d'envoi de message
document.addEventListener("DOMContentLoaded", () => {
    const userId = localStorage.getItem('userId');
    if (window.location.pathname.includes("chat.html") && userId) {
        fetchUserConversations(userId);

        document.getElementById('sendButton').addEventListener('click', () => {
            const conversationId = document.getElementById('conversationId').value;
            const content = document.getElementById('messageInput').value;

            if (conversationId && content) {
                sendMessage(conversationId, userId, content);
            } else {
                alert("Veuillez remplir tous les champs !");
            }
        });
    }

    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', (event) => {
            event.preventDefault();
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            loginUser(email, password);
        });
    }
});
