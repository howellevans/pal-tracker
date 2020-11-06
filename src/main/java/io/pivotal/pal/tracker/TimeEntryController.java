package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class TimeEntryController {

    private long id;
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;
    @Autowired
    TimeEntryRepository repository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.repository = timeEntryRepository;
    }


    @PostMapping(value = "/time-entries",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity create(@RequestBody  TimeEntry timeEntryToCreate) {
         TimeEntry result = repository.create(timeEntryToCreate);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(value = "/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable("timeEntryId") long timeEntryId) {
        TimeEntry result = repository.find(timeEntryId);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping(value = "/time-entries",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> result = repository.list();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/time-entries/{timeEntryId}",
          produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TimeEntry> update(@PathVariable("timeEntryId") long timeEntryId, @RequestBody  TimeEntry expected) {
        TimeEntry result = repository.update(timeEntryId, expected);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/time-entries/{timeEntryId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity delete(@PathVariable("timeEntryId") long timeEntryId) {
        repository.delete(timeEntryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
