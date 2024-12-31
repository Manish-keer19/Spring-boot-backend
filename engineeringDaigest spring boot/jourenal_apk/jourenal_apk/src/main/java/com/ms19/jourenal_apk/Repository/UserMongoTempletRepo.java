package com.ms19.jourenal_apk.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.ms19.jourenal_apk.entity.userModel;

@Component
public class UserMongoTempletRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<userModel> getUserThatHasEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        List<userModel> users = mongoTemplate.find(query, userModel.class);

        return users;
    }
}
