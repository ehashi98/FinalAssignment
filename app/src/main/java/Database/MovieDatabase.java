package Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * @author Ece Selin Yorgancilar
 * This abstract class extends RoomDatabase.
 * Let the program know that there is an entity database called MovieInfo
 */
@Database(entities = {MovieInfo.class},version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDAO mDAO();

}
