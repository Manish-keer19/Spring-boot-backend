package com.ms19.jourenal_apk.Services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ms19.jourenal_apk.Repository.JournaleEntryRepo;
import com.ms19.jourenal_apk.Repository.UserRepo;
import com.ms19.jourenal_apk.entity.journalEntryModel;
import com.ms19.jourenal_apk.entity.userModel;

@Component
public class JournalEntryServices {

    @Autowired
    private JournaleEntryRepo journaleEntryRepo;

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepo userRepo;

    @Transactional
    public journalEntryModel saveEntry(journalEntryModel myEntry, String userName) {
        userModel user = userRepo.findByuserName(userName);
        if (user == null) {
            return null;
        }
        journalEntryModel journalEntry = journaleEntryRepo.save(myEntry);
        user.getJournalEntries().add(journalEntry);
        userServices.SaveUser(user);
        return journalEntry;

    }

    public List<journalEntryModel> getAllEntries() {
        return journaleEntryRepo.findAll(); // Return all entries as a List
    }

    public Optional<journalEntryModel> getOneEntry(ObjectId id) {
        return journaleEntryRepo.findById(id);
    }

    public Optional<journalEntryModel> DeleteEntry(ObjectId myId, String userName) {
        Optional<journalEntryModel> entry = journaleEntryRepo.findById(myId);
        if (entry.isPresent()) {
            userModel user = userRepo.findByuserName(userName);
            user.getJournalEntries().removeIf(x -> x.getId().equals(myId));
            userServices.SaveUser(user);
            journaleEntryRepo.deleteById(myId);
        }
        return entry; // Return the deleted entity
    }

    public journalEntryModel updateOneEntry(ObjectId myid, journalEntryModel newEtry) {

        System.out.println("newEntry is " + newEtry);

        journalEntryModel oldEntry = journaleEntryRepo.findById(myid).orElse(null);

        if (oldEntry != null) {
            oldEntry.setContent(newEtry.getContent() != null && !newEtry.getContent().equals("") ? newEtry.getContent()
                    : oldEntry.getContent());

            oldEntry.setTitle(newEtry.getTitle() != null && !newEtry.getTitle().equals("") ? newEtry.getTitle()
                    : oldEntry.getTitle());
        }

        journaleEntryRepo.save(oldEntry);
        return oldEntry;

    }

}
