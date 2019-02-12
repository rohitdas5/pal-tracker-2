package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public class TimeEntryController {

    @Autowired
    private TimeEntryRepository timeEntriesRepo;

    public TimeEntryController(TimeEntryRepository timeEntriesRepo) {
    }

    public ResponseEntity<TimeEntry> create(TimeEntry timeEntryToCreate) {
        timeEntryToCreate.setId(1);

        TimeEntry createTimeEntry = timeEntriesRepo.create(timeEntryToCreate);
        return new ResponseEntity<>(createTimeEntry, HttpStatus.CREATED);

    }


    public ResponseEntity<TimeEntry> update(long id, TimeEntry timeEntry) {

        TimeEntry updatedTimeEntry = timeEntriesRepo.update(id, timeEntry);
        if (timeEntry != null) {
            return new ResponseEntity<>(timeEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    public ResponseEntity<TimeEntry> delete(long id) {
        timeEntriesRepo.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<>(timeEntriesRepo.list(), HttpStatus.OK);
    }

    public ResponseEntity<TimeEntry> read(long id) {
        TimeEntry timeEntry = timeEntriesRepo.find(id);
        if (timeEntry != null) {
            return new ResponseEntity<>(timeEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
