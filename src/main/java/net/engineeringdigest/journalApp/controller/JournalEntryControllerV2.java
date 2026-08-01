package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?>getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();

        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity <JournalEntry>  createEntry(@RequestBody JournalEntry myEntry ,@PathVariable String userName) {
       try {
           journalEntryService.saveEntry(myEntry,userName);
           return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
       }
       catch (Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if (journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @DeleteMapping("id/{userName}/{myID}")
    public ResponseEntity<?>DeleteJournalEntryById(@PathVariable ObjectId myID ,@PathVariable String userName){
        journalEntryService.deleteBYId(myID,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateJournalEntryByID(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        JournalEntry old = journalEntryService.findById(id).orElse(null);

//        if (old != null) {
//            // Fixed: Check if new title is not null and not empty
//            old.setTittle(newEntry.getTittle() != null && !newEntry.getTittle().isEmpty() ? newEntry.getTittle() : old.getTittle());
//
//            // FIXED BUG: Changed newEntry.equals("") to !newEntry.getContent().isEmpty()
//            old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
//
//            journalEntryService.saveEntry(old, user);
//            return new ResponseEntity<>(old, HttpStatus.OK);
//        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}