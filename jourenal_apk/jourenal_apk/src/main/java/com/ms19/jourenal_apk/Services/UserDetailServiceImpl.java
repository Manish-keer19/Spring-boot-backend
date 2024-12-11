package com.ms19.jourenal_apk.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.ms19.jourenal_apk.Repository.UserRepo;
import com.ms19.jourenal_apk.entity.userModel;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userModel user = userRepo.findByuserName(username);

        if (user != null) {
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName()).password(user.getPassword())
                    .roles(user.getRole().toArray(new String[0])).build();

            return userDetails;
        }
        throw new UsernameNotFoundException("usrename is not found" + username);

    }
}
