package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<JournalEntry> getAll(){
         return journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry  createEntry(@RequestBody JournalEntry myEntry) {
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;

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
    public Boolean DeleteJournalEntryById(@PathVariable ObjectId myID){
        journalEntryService.deleteBYId(myID);
        return true;
    }

    @PutMapping("id/{id}")
    public JournalEntry UpdateJournalEntryByID(@PathVariable ObjectId id,@RequestBody JournalEntry newEntry){
        JournalEntry Old=journalEntryService.findById(id).orElse(null);
        if(Old!=null){
            Old.setTittle(
                    newEntry.getTittle()!=null
                    &&!newEntry.getTittle()
                    .equals("")?newEntry.
                    getTittle():Old.getTittle());
            Old.setContent(
                    newEntry.getContent()!= null
                    &&!newEntry.equals("")?newEntry
                    .getContent(): Old.getContent());

        }
        journalEntryService.saveEntry(Old);
        return Old;

    }







}
