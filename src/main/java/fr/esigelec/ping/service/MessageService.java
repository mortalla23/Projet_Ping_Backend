package fr.esigelec.ping.service;

import fr.esigelec.ping.model.Conversation;
import fr.esigelec.ping.model.Message;

import fr.esigelec.ping.repository.ConversationRepository;
import fr.esigelec.ping.repository.MessageRepository;
import fr.esigelec.ping.websocket.WebSocketService;
import fr.esigelec.ping.websocket.WebSocketService2;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.data.mongodb.core.query.Update;


@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    
    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

     @Autowired
    private WebSocketService webSocketService;

    
    @Autowired
    private WebSocketService2 webSocketService2;

   

    //Récupérer les messages d'une conversation
    public List<Message> getMessages(int conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }
     
    // 🔍 Récupérer un message par son id
    public Message getMessageById(int messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Le message avec l'ID " + messageId + " n'existe pas."));
    }

    // 🔄 Marquer un message comme lu en ciblant le champ "id"
    public void markMessageAsRead(int messageId) {
        // 📌 Crée une requête qui cible le champ "id" (ton identifiant métier)
        Query query = new Query(Criteria.where("id").is(messageId));

        // 🔄 Mise à jour du champ "isRead"
        Update update = new Update().set("isRead", true);

        // ⚡ Exécuter la mise à jour sans utiliser _id
        mongoTemplate.updateFirst(query, update, Message.class);
    }


   // 🔧 ➕ Création d'un message et mise à jour de last_message_id
    public Message addMessage(int conversationId, int userId, String content) {
        // ✅ Vérifier si la conversation existe
        if (!conversationRepository.existsById(conversationId)) {
            throw new IllegalArgumentException("La conversation avec l'ID " + conversationId + " n'existe pas.");
        }

        // 🔄 Générer un ID unique pour le message
        int id = generateUniqueMessageId();

        // ➕ Créer le message
        Message message = new Message();
        message.setId(id);
        message.setConversationId(conversationId);
        message.setSenderId(userId);
        message.setContent(content);
        message.setIsRead(false);
        message.setCreatedAt(new Date());

        // 💾 Sauvegarder le message
        Message savedMessage = messageRepository.save(message);

        // 🔊 Diffusion du message via WebSocket
        webSocketService.broadcastMessage(conversationId, content, userId);
        webSocketService2.broadcastMessage(conversationId,userId, content);
        webSocketService2.broadcastNotification(conversationId, userId, content);
        System.out.println("🔔 Diffusion du message dans la conversation : " + conversationId);


        // 🔄 Mettre à jour le last_message_id dans la conversation
        Query query = new Query(Criteria.where("id").is(conversationId));  // Recherche par champ `id`
        Update update = new Update().set("last_message_id", savedMessage.getId());

        // ✅ Mise à jour sans duplication
        mongoTemplate.updateFirst(query, update, Conversation.class);

        return savedMessage;
    }


    



    // 🔄 Génération d'un ID unique pour le message
    private int generateUniqueMessageId() {
        int id;
        do {
            id = (int) (Math.random() * 100000);
        } while (messageRepository.existsById(id));
        return id;
    }
     
}
