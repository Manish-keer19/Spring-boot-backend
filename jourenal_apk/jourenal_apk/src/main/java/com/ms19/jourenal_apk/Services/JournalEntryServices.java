package com.ms19.jourenal_apk.Services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms19.jourenal_apk.Repository.JournaleEntryRepo;
import com.ms19.jourenal_apk.entity.journalEntryModel;

@Component
public class JournalEntryServices {

    @Autowired
    private JournaleEntryRepo journaleEntryRepo;

    public void saveEntry(journalEntryModel myEntry) {
        journaleEntryRepo.save(myEntry);
    }

    public List<journalEntryModel> getAllEntries() {
        return journaleEntryRepo.findAll(); // Return all entries as a List
    }

    public Optional<journalEntryModel> getOneEntry(ObjectId id) {
        return journaleEntryRepo.findById(id);
    }

    public Optional<journalEntryModel> DeleteEntry(ObjectId myId) {
        Optional<journalEntryModel> entry = journaleEntryRepo.findById(myId);
        if (entry.isPresent()) {
            journaleEntryRepo.deleteById(myId);
        }
        return entry; // Return the deleted entity
    }

    @SuppressWarnings("null")
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
