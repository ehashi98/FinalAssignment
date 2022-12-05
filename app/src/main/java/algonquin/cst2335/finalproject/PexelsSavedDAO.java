package algonquin.cst2335.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PexelsSavedDAO {

    @Insert
    void saveImage(PexelsSaved img);

    @Query("Select * from PexelsSaved")
    List<PexelsSaved> getAllImages();

    @Delete
    void deleteImage(PexelsSaved img);
}
