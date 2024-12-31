package com.spring.ai.springAI.Controller;


import com.spring.ai.springAI.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenAiController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/get-ai-res")
    public  String getAiRes(@RequestParam String prompt){
        return  chatService.getModelRes(prompt);
    }

}
