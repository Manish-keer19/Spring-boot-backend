package com.ms19.jourenal_apk.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ms19.jourenal_apk.entity.userModel;

public interface UserRepo extends MongoRepository<userModel,ObjectId> {
    userModel findByuserName(String userName);
    userModel deleteByuserName(String userName);
}
