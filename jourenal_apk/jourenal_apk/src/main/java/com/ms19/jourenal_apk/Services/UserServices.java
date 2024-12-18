package com.ms19.jourenal_apk.Services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ms19.jourenal_apk.Repository.UserRepo;
import com.ms19.jourenal_apk.entity.userModel;

@Service
public class UserServices {

  @Autowired
  private UserRepo userRepo;

  private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public userModel saveNewUser(userModel user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(Arrays.asList("USER"));
    return userRepo.save(user);

  }

  public userModel saveAdmin(userModel user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(Arrays.asList("USER", "ADMIN"));
    return userRepo.save(user);

  }

  public void saveUser(userModel user) {
    userRepo.save(user);
  }

  public List<userModel> getAllUser() {
    return userRepo.findAll();
  }

  public userModel updateUser(String username, userModel user) {
    userModel dbuser = userRepo.findByuserName(username);
    if (dbuser == null) {
      return null;
    }
    dbuser.setUserName(user.getUserName());
    dbuser.setPassword(user.getPassword());
    return saveNewUser(dbuser);

  }

  public userModel findByUsername(String username) {
    return userRepo.findByuserName(username);
  }

}
