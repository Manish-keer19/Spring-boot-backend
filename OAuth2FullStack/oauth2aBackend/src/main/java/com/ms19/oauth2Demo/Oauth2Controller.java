package com.ms19.oauth2Demo;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Oauth2Controller {

    @GetMapping("/success")
    public Map<?,?> done(@AuthenticationPrincipal OAuth2User oAuth2User){
        return  oAuth2User.getAttributes();
    }
}
