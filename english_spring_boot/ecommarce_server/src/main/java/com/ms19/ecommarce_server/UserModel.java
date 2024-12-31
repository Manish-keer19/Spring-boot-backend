package com.ms19.ecommarce_server;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserModel {

    private String username;

    private  int userId;
    private  String Role;

}
