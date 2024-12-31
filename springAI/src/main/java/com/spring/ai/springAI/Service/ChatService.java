package com.spring.ai.springAI.Service;




import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {


    @Autowired
    private ChatModel chatModel;


    public  String getModelRes (String prompt){
        return  chatModel.call(prompt);


    }
}
