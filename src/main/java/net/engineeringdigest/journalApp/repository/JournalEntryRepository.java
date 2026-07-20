package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.yaml.snakeyaml.events.Event;

public interface JournalEntryRepository extends MongoRepository<JournalEntry,String> {





}
