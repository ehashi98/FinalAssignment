package algonquin.cst2335.ticketmaster.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * This is the event database class which extend the RoomDatabase.
 * @author kamelia
 * @version 1.0
 */
@Database(entities = {Event.class}, version = 1)
public abstract class EventDatabase extends RoomDatabase {
    public abstract EventDAO eventDAO();
}