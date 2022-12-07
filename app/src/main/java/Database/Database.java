package Database;
import android.app.Application;
import android.content.Context;

import androidx.room.Room;

/**
 * @author Ece Selin Yorgancilar
 * Use it to create a Database and let other classes have access to it (singleton)
 */
public class Database extends Application {
    private static MovieDatabase Database = null;

    /**
     * this is a static method which returns a shared database to activities
     * @param context the Context object
     * @return a moviedatabase database
     */
    public static MovieDatabase getSharedDatabase(Context context) {

        if (Database == null) {
            synchronized (MovieDatabase.class) {
                if (Database == null) {
                    Database = Room.databaseBuilder(context, MovieDatabase.class, "MovieInfo Database").build();
                }
            }
        }
        return Database;
    }
}



