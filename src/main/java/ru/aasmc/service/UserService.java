package ru.aasmc.service;

import org.springframework.stereotype.Component;
import ru.aasmc.model.User;

import java.util.UUID;

@Component
public class UserService {

    public User findById(String id) {
        String randomName = UUID.randomUUID().toString();
        // always "finds" the user, every user has a random name
        return new User(id, randomName);
    }
}