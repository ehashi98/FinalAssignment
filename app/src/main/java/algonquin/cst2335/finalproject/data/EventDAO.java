package algonquin.cst2335.finalproject.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * This is the databast DAO class where the all the event will get from the database.
 * @author kamelia
 * @version 1.0
 *
 */
@Dao
public interface EventDAO {
    @Insert
    public long insertEvent(Event e);

    @Query("SELECT * FROM Event")
    List<Event> getAllEvent();

    @Delete
    void deleteEvent(Event e);
}