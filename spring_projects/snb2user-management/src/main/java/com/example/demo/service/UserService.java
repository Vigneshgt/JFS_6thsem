package com.example.demo.service;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public User register(User user) {
        return repo.save(user);
    }
    public List<User> getAllUsers(){
    	return repo.findAll();
    }

    public User login(String username, String password) {
        User user = repo.findByUsername(username);
        if(user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}






















