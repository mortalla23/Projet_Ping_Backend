package fr.esigelec.ping.service;

import org.springframework.stereotype.Service;

import fr.esigelec.ping.model.User;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Service
public class TempStorageService {
    private ConcurrentMap<String, User> tempUserStorage = new ConcurrentHashMap<>();

    public void storeUser(String email, User user) {
        tempUserStorage.put(email, user);
    }

    public User getUser(String email) {
        return tempUserStorage.get(email);
    }

    public void removeUser(String email) {
        tempUserStorage.remove(email);
    }
}

