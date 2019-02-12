package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TimeEntryRepository {

    TimeEntry create (TimeEntry timeEntry);
    TimeEntry find(Long id);
    List<TimeEntry> list();
    TimeEntry update(Long id, TimeEntry timeEntry);
    void delete(Long id);

}
