package com.manishkeer.Journal.apk.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manishkeer.Journal.apk.entity.journalModel;

@RestController
@RequestMapping("/api")
class JournalEntryController {

    private HashMap<String, journalModel> JournalEntrys = new HashMap<>();

    @GetMapping
    public String goodMethod() {
        return "all are good";
    }

    @GetMapping("/getAllEntry")
    public ArrayList<journalModel> getall() {
        return new ArrayList<>(JournalEntrys.values());
    }

    @PostMapping("/add-entry")
    public Boolean CreatEntry(@RequestBody journalModel entry) {
        JournalEntrys.put(entry.getId(), entry);
        return true;
    }

    @GetMapping("/id/{Id}")
    public journalModel getEntryById(@PathVariable String Id) {
        return JournalEntrys.get(Id);  
    }

    @DeleteMapping("/id/{id}")
    public Boolean deleteEntryById(@PathVariable String id) {
        JournalEntrys.remove(id);
        return true;
    }

    @PutMapping("/id/{Id}")
    public journalModel updateEntryById (@PathVariable String Id, @RequestBody journalModel Myentry){
        JournalEntrys.put(Id, Myentry);
        return JournalEntrys.get(Id);
    }

}