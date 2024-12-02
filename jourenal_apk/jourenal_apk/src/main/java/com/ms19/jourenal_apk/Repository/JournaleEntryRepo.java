package com.ms19.jourenal_apk.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ms19.jourenal_apk.entity.journalEntryModel;

public interface JournaleEntryRepo extends MongoRepository<journalEntryModel, ObjectId>  {
    
}
