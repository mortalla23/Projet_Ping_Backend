package fr.esigelec.ping.service;

import fr.esigelec.ping.model.Message;
import fr.esigelec.ping.repository.MessageRepository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message sendMessage(Message message) {
        // Assurez-vous d'ajouter la date de création au message
        message.setCreatedAt(new Date());
        message.setRead(false); // Le message est initialement non lu

        // Sauvegarder le message dans la base de données
        return messageRepository.save(message);
    }

    public List<Message> getMessages(int conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }
}
