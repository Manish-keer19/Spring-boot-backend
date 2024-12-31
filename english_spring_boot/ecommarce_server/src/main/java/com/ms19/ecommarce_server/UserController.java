package com.ms19.ecommarce_server;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private  UserModel userModel;

    @GetMapping("user")
    public  UserModel getUser(){
        UserModel user = new UserModel();

        user.setRole("admin");
        user.setUsername("manihs keer");
        user.setUserId(200);
        return user;


    }
}
