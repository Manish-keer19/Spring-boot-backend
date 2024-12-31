package com.ms19.jourenal_apk.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;




// it is a project lombok anotation it is used to generate autoamtic getter ans setter
// @Getter
// @Setter
// @Data





@Data
@Document(collection = "journalEntry")
public class journalEntryModel {
    @Id
    private ObjectId id;
    // genrate the the id title and content
    private String title;
    private String content;

   

    // public String getTitle() {
    //     return title;
    // }

    // public void setTitle(String title) {
    //     this.title = title;
    // }

    // public String getContent() {
    //     return content;
    // }

    // public void setContent(String content) {
    //     this.content = content;
    // }
    // public void setid(ObjectId id) {
    //     this.id = id;
    // }
    // public ObjectId getid() {
    //     return id;
    // }
}
