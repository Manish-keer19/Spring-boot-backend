package com.ms19.jourenal_apk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.ms19.jourenal_apk.Repository.UserRepo;
import com.ms19.jourenal_apk.Response.Response;
import com.ms19.jourenal_apk.Services.UserServices;
import com.ms19.jourenal_apk.entity.userModel;

@RestController
@RequestMapping("api/v1/")
public class UserController {

    @Autowired
    private UserServices userServices;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/saveUser")
    public Response CreateUser(@RequestBody userModel user) {
        System.out
                .println("user in usercontroller: userName=" + user.getUserName() + ", password=" + user.getPassword());

        try {
            userServices.SaveUser(user);
            return new Response(200, true, "user created successfully", null);
        } catch (Exception e) {
            return new Response(400, false, "could not create the user", e.getMessage());
        }
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<Response> getAllUser() {
        try {

            List<userModel> users = userServices.getAllUser();
            return new ResponseEntity<Response>(new Response(200, true, "user fechted successfully", null, users),
                    HttpStatusCode.valueOf(200));

        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<Response>(new Response(400, false, "could not fetch the user", e.getMessage()),
                    HttpStatusCode.valueOf(400));
        }
    }

    @PutMapping("/user/updateUser")
    public Response updateUser(@RequestBody userModel user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            userModel dbuser = userServices.updateUser(username, user);
            if (dbuser == null) {
                return new Response(404, false, "user not found", null, null);
            }
            return new Response(200, true, "user updated succefully", null, dbuser);
        } catch (Exception e) {
            return new Response(400, false, "could not update the user", e.getMessage());
        }
    }

    @DeleteMapping("/user/delete-user")
    public ResponseEntity<Response> deleteUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userModel user = userRepo.deleteByuserName(authentication.getName());
        return new ResponseEntity<Response>(new Response(200, true, "user deleted successfully", null, user),
                HttpStatusCode.valueOf(200));

    }

}
