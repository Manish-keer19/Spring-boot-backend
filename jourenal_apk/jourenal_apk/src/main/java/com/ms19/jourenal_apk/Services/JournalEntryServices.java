package com.ms19.jourenal_apk.Services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms19.jourenal_apk.Repository.JournaleEntryRepo;
import com.ms19.jourenal_apk.Repository.UserRepo;
import com.ms19.jourenal_apk.entity.journalEntryModel;
import com.ms19.jourenal_apk.entity.userModel;

// @Component
@Service
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
        userServices.saveUser(user);
        return journalEntry;

    }

    public List<journalEntryModel> getJournalEntriesByUserName(String userName) {
        userModel user = userServices.findByUsername(userName);
        if (user != null) {
            return user.getJournalEntries();
        } else {
            return null;
        }
    }

    public Optional<journalEntryModel> getOneEntry(ObjectId id, String userName) {
        // Find user by username
        userModel user = userServices.findByUsername(userName);

        // If user is not found, return an empty Optional
        if (user == null) {
            return Optional.empty();
        }

        // Use Stream API to find the first journal entry with the matching ID
        return user.getJournalEntries()
                .stream()
                .filter(entry -> entry.getId().equals(id)) // Match journal entry by ID
                .findFirst(); // Return the first matching journal entry as Optional
    }

    public Optional<journalEntryModel> DeleteEntry(ObjectId myId, String userName) {
        Optional<journalEntryModel> entry = journaleEntryRepo.findById(myId);
        if (entry.isPresent()) {
            userModel user = userRepo.findByuserName(userName);
            user.getJournalEntries().removeIf(x -> x.getId().equals(myId));
            userServices.saveUser(user);
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
