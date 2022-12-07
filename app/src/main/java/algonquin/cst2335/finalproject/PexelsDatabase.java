package algonquin.cst2335.finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PexelsModel.class}, version = 1)
public abstract class PexelsDatabase extends RoomDatabase {
    public abstract PexelsSavedDAO psDao();
}
