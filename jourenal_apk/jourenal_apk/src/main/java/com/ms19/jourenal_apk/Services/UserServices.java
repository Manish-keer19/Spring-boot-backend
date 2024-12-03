package com.ms19.jourenal_apk.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms19.jourenal_apk.Repository.UserRepo;
import com.ms19.jourenal_apk.entity.userModel;

@Service
public class UserServices {

    @Autowired
    private UserRepo userRepo;

    public void SaveUser(userModel user) {
        System.out.println("user in userservice " + user);
        userRepo.save(user);
        System.out.println("user saved succefully");

    }

    public List<userModel> getAllUser(){
        return userRepo.findAll();
    }

    public userModel updateUser(userModel user){
      userModel dbuser = userRepo.findByuserName(user.getUserName());
      if (dbuser == null) {
        return null;
      }
       dbuser.setUserName(user.getUserName());
       dbuser.setPassword(user.getPassword());
     userRepo.save(dbuser);
       return dbuser;

    }



}
