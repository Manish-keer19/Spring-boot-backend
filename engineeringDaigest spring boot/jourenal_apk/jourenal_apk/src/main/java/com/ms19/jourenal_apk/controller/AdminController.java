package com.ms19.jourenal_apk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms19.jourenal_apk.Response.Response;
import com.ms19.jourenal_apk.Services.UserServices;
import com.ms19.jourenal_apk.entity.userModel;

@RestController
@RequestMapping("/api/v1/Admin")
public class AdminController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/get-all-user")
    public Response getAllUser() {
        // Authentication authentication =
        // SecurityContextHolder.getContext().getAuthentication();
        // String userName = authentication.getName();
        List<userModel> allUser = userServices.getAllUser();

        if (allUser != null && !allUser.isEmpty()) {
            return new Response(200, true, "user fetched succesfully", null, allUser);

        }
        return new Response(400, false, "could no get the all user ", null);

    }

    @PostMapping("/creat-admin")
    public Response createAdmin(@RequestBody userModel user) {

        userServices.saveAdmin(user);
        return new Response(201, true, "admin created succesfully");
    }

}
