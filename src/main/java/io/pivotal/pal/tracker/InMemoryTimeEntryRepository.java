package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private HashMap<Long, TimeEntry> timeEntries = new HashMap<>();
    private long currentId = 1L;


    public TimeEntry create(TimeEntry timeEntry) {
        Long id = currentId++;

        TimeEntry newTimeEntry = new TimeEntry();
        newTimeEntry.setId(id);
             newTimeEntry.setProjectId(timeEntry.getProjectId());
                newTimeEntry.setUserId(timeEntry.getUserId());
                newTimeEntry.setDate(timeEntry.getDate());
                newTimeEntry.setHours(timeEntry.getHours());

        timeEntries.put(id,newTimeEntry);
        return newTimeEntry;
    }

    @Override
    public TimeEntry find(Long id){

        return timeEntries.get(id);
    }
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        if(find(id)==null) return null;

        TimeEntry newTimeEntry = new TimeEntry(
                id,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );
        timeEntries.put(id,newTimeEntry);
        return newTimeEntry;
    }

    public List<TimeEntry> list(){
        return new ArrayList<TimeEntry>(timeEntries.values());
    }


    public void delete(Long id) {
       timeEntries.remove(id);

    }

}


