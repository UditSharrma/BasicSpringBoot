package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;



    @GetMapping
    public ResponseEntity<?>getAll() {
        List<JournalEntry> all = journalEntryService.getAll();

        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity <JournalEntry>  createEntry(@RequestBody JournalEntry myEntry) {
       try {

           myEntry.setDate(LocalDateTime.now());
           journalEntryService.saveEntry(myEntry);
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


    @DeleteMapping("id/{myID}")
    public ResponseEntity<?>DeleteJournalEntryById(@PathVariable ObjectId myID){
        journalEntryService.deleteBYId(myID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateJournalEntryByID(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        JournalEntry old = journalEntryService.findById(id).orElse(null);

        if (old != null) {
            // Fixed: Check if new title is not null and not empty
            old.setTittle(newEntry.getTittle() != null && !newEntry.getTittle().isEmpty() ? newEntry.getTittle() : old.getTittle());

            // FIXED BUG: Changed newEntry.equals("") to !newEntry.getContent().isEmpty()
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());

            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}