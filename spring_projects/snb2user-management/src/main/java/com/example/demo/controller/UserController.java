package com.example.demo.controller;


import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService service;
    
    	@GetMapping("/")
    		public String home() {
    			return "Backend running successfully"; 
    	}
    	
    	@GetMapping("/users")
    	public List<User> getAllUsers(){
    		return service.getAllUsers();
    	}

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return service.login(user.getUsername(), user.getPassword());
    }
}



























