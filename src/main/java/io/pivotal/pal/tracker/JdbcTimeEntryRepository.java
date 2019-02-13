package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository{


    private final JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

   public TimeEntry create (TimeEntry timeEntry) {
/*       final String updateSql = "INSERT INTO time_entries (project_id, user_id, date, hours) VALUES (?, ?, ?, ?)";
       int timeEntryId= jdbcTemplate.update(updateSql,
                timeEntry.getProjectId(),timeEntry.getUserId(),timeEntry.getDate(),timeEntry.getHours());
        return find(((long)timeEntryId));*/
       KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

       jdbcTemplate.update(connection -> {
           PreparedStatement statement = connection.prepareStatement(
                   "INSERT INTO time_entries (project_id, user_id, date, hours) " +
                           "VALUES (?, ?, ?, ?)",
                   RETURN_GENERATED_KEYS
           );

           statement.setLong(1, timeEntry.getProjectId());
           statement.setLong(2, timeEntry.getUserId());
           statement.setDate(3, Date.valueOf(timeEntry.getDate()));
           statement.setInt(4, timeEntry.getHours());

           return statement;
       }, generatedKeyHolder);

       return find(generatedKeyHolder.getKey().longValue());
    }

    //This is for getting the data and assigning to the resultset.
    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;



    public void delete(Long id){
        jdbcTemplate.update("delete from time_entries where id="+id);
    }

    public TimeEntry find(Long id){
        return jdbcTemplate.query(
                "SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = ?",
                new Object[]{id},
                extractor);


    }

   public TimeEntry update(Long id, TimeEntry timeEntry){

        final String updateSql = "UPDATE time_entries SET project_id=?, user_id=?,date=?,hours=? WHERE id = ?";
        jdbcTemplate.update(updateSql,
                timeEntry.getProjectId(),timeEntry.getUserId(),timeEntry.getDate(),timeEntry.getHours(),id);

        return find(id);
    }

    public List<TimeEntry> list(){
       try {
           List<TimeEntry> timeEntryList = jdbcTemplate.query("select * from time_entries", mapper);
           return timeEntryList;
       }catch(Exception ex){
           return null;
       }
    }

}
