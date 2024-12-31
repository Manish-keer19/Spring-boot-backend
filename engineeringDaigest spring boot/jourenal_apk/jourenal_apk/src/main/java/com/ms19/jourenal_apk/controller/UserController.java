package com.ms19.jourenal_apk.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.ms19.jourenal_apk.Repository.UserMongoTempletRepo;
import com.ms19.jourenal_apk.Repository.UserRepo;
import com.ms19.jourenal_apk.Response.Response;
import com.ms19.jourenal_apk.Services.EmailService;
import com.ms19.jourenal_apk.Services.UserDetailServiceImpl;
import com.ms19.jourenal_apk.Services.UserServices;
import com.ms19.jourenal_apk.Services.WeatherService;
import com.ms19.jourenal_apk.entity.userModel;
import com.ms19.jourenal_apk.utils.JwtUtil;
import com.ms19.jourenal_apk.weatherApiRes.WeatherApiRes;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1")
@Slf4j
public class UserController {

    @Autowired
    private UserServices userServices;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private UserMongoTempletRepo userMongoTempletRepo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @PostMapping("/saveUser")
    public Response CreateUser(@RequestBody userModel user) {
        System.out
                .println("user in usercontroller: userName=" + user.getUserName() + ", password=" + user.getPassword());

        try {
            userServices.saveNewUser(user);
            return new Response(200, true, "user created successfully", null);
        } catch (Exception e) {
            log.error("could not create user ");
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
            return new ResponseEntity<Response>(new Response(400, false, "could not fetch the user", e.getMessage()),
                    HttpStatusCode.valueOf(400));

        }
    }

    @PutMapping("/user/updateUser")
    public Response updateUser(@RequestBody userModel user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication is " + authentication);
        ;
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

    @GetMapping("/greet")
    public Response greet() {  
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        WeatherApiRes res = weatherService.getWeather("Indore");

        if (res != null) {
            return new Response(200, true, "hello" + username, null, res);
        }
        return new Response(200, true, "Hello  " + username + "!", null);
    }

    @GetMapping("/getusers/{email}")
    public List<userModel> getUserThatHasEmail(@PathVariable String email) {

        List<userModel> users = userMongoTempletRepo.getUserThatHasEmail(email);
        return users;
    }

    @GetMapping("/send-mail/{email}")
    public Response sendMail(@PathVariable String email) {

        emailService.SendMail(email, "for Testing email", "hello this email is being tested by manish");
        return new Response(200, true, "email send succefully");

    }

    // @Scheduled(cron = "0 * * * * *") for every second
    @Scheduled(cron = "0 0 9 * * SUN") /* every sun 9:00AM */
    public Response SendMailAuto() {
        log.info("message is sending to manishkeer530@gmail.com");
        emailService.SendMail("manishkeer530@gmail.com", "for Testing email",
                "every minut you will get this message");
        return new Response(200, true, "email sent successfully");
    }

    @PostMapping("/login")
    public Response login(@RequestBody userModel user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new Response(200, true, "user login succefully", null, jwt);
        } catch (Exception e) {
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new Response(401, false, "invalid username or password", null, null);
        }
    }
}
