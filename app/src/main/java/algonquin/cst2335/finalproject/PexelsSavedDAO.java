package algonquin.cst2335.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PexelsSavedDAO {

    @Insert
    void saveImage(PexelsModel img);

    @Query("Select * from PexelsModel")
    List<PexelsModel> getAllImages();

    @Delete
    void deleteImage(PexelsModel img);
}
