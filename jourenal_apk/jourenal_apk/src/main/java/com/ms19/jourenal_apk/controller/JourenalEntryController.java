package com.ms19.jourenal_apk.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ms19.jourenal_apk.Services.JournalEntryServices;
import com.ms19.jourenal_apk.entity.journalEntryModel;
import com.ms19.jourenal_apk.Response.*;




@RestController
@RequestMapping("api/v1")
public class JourenalEntryController {
    
    @Autowired
    private JournalEntryServices journalEntryServices;

    @GetMapping("/greet")
    public String greet() {
        return "hello from manish";
    }

    @PostMapping("/createEntry")
    public Response createEntry(@RequestBody journalEntryModel myEntry) {
        try {

            journalEntryServices.saveEntry(myEntry);
            return new Response(200, true, "journal entry created succefully");
        } catch (Exception e) {

            return new Response(0, false, "could not create entry", e.getMessage());
        }

    }

    @GetMapping("/getAllEntries")
    public Response getAllEntries() {
        try {

            List<journalEntryModel> Entrys = journalEntryServices.getAllEntries();
            return new Response(200, true, "journal entry fechted succefully", null, Entrys);

        } catch (Exception e) {

            return new Response(400, false, "couold not fetch entries", e.getMessage());
        }
    }

    @GetMapping("/getOneEntry/{myid}")
    public Response getEntry(@PathVariable ObjectId myid) {

        System.out.println("myid" + myid);
        try {

            Object entry = journalEntryServices.getOneEntry(myid).orElse(null);
            return new Response(200, true, "journal entry fechted succefully", null, entry);

        } catch (Exception e) {

            return new Response(400, false, "could not fetch entry", e.getMessage());
        }

    }

    @DeleteMapping("/deleteEntry/{myid}")
    public ResponseEntity<Response> deleteOnEntry(@PathVariable("myid") ObjectId myId) {
        try {
            // Attempt to delete the entry
            Object deletedEntry = journalEntryServices.DeleteEntry(myId).orElse(null);

            if (deletedEntry == null) {
                // Entry not found, return 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new Response(404, false, "Entry not found", null, null));
            }

            // Successfully deleted, return 200
            return ResponseEntity.ok(
                    new Response(200, true, "Entry deleted successfully", null, deletedEntry));

        } catch (IllegalArgumentException e) {
            // Handle invalid ObjectId or other argument issues
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new Response(400, false, "Invalid ID format", e.getMessage(), null));
        } catch (Exception e) {
            // Handle general exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response(500, false, "Could not delete the Entry", e.getMessage(), null));
        }
    }

    @PutMapping("/updateEntry/{myid}")
    public Response updateEntry(@PathVariable("myid") ObjectId myid, 
    @RequestBody journalEntryModel entryModel) {
        try {
            Object Entry = journalEntryServices.updateOneEntry(myid, entryModel);
            return new Response(200, true, "entry update succefully", null, Entry);
    
        } catch (Exception e) {
            return new Response(500, false, "could not update the Entry", e.getMessage(), null);

        }
    }

}
