package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;



    @GetMapping
    public List<JournalEntry> getAll(){
         return null;
    }

    @PostMapping
    public boolean  createEntry(@RequestBody JournalEntry myEntry) {
        journalEntryService.saveEntry(myEntry);
        return true;

    }
    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable Long myId){
        return null;
    }

    @DeleteMapping("id/{myID}")
    public JournalEntry DeleteJournalEntryById(@PathVariable Long myID){
        return null;
    }

    @PutMapping("id/{id}")
    public JournalEntry UpdateJournalEntryByID(@PathVariable Long id,@RequestBody JournalEntry myEntry){
        return null;
    }







}
