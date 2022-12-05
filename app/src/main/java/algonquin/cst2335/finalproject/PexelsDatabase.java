package algonquin.cst2335.finalproject;

import androidx.room.Database;

@Database(entities = {PexelsSaved.class}, version = 1)
public abstract class PexelsDatabase {
    public abstract PexelsSavedDAO psDao();
}
