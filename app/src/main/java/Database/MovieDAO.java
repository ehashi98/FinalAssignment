package Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
/**
 * @author Ece Selin Yorgancilar
 * DAO class for the database queries. Insert, Delete and Select statements.
 */
public interface MovieDAO {
    @Insert
    void insertMovieInfo(MovieInfo m);

    @Query( "Select * from MovieInfo")
    List<MovieInfo> getAllInformation();

    @Delete
    void deleteInformation(MovieInfo m);
}
