package Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author Ece Selin Yorgancilar
 * Create database columns for the movie information app
 * It includes all the information that will be displayed
 * at where all movie information title provided.
 */
@Entity
public class MovieInfo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name="imdbID")
    protected String imdbID;

    @ColumnInfo(name = "title")
    protected String movieTitle;

    @ColumnInfo(name = "year")
    protected String year;

    @ColumnInfo(name = "runtime")
    protected String runtime;

    @ColumnInfo(name = "rating")
    protected String rating;

    @ColumnInfo(name = "plot")
    protected String plot;

    @ColumnInfo(name = "mainActor")
    protected String mainActor;

    @ColumnInfo(name="posterUrl")
    protected  String posterUrl;

    /**
     * empty constructor
     */
    public MovieInfo(){}

    /**
     * Constructor with all the required information variables, to be used later.
     * @param movieTitle title of the movie
     * @param imdbID  id of the movie
     * @param year  released date of the movie
     * @param rating rating of the movie.
     * @param runtime how long the movie runs
     * @param mainActor main actors of the movie
     * @param plot plot of the movie
     * @param posterUrl url of the movie poster
     */
    public MovieInfo(String movieTitle,String imdbID,String year,String rating,String runtime,String mainActor,String plot, String posterUrl){
        this.movieTitle=movieTitle;
        this.runtime=runtime;
        this.mainActor=mainActor;
        this.rating=rating;
        this.plot=plot;
        this.imdbID=imdbID;
        this.year=year;
        this.posterUrl=posterUrl;
    }

    /**
     * getter for movie rating
     * @return movie rating
     */
    public String getRating(){return rating;}

    /**
     * getter for movie runtime
     * @return how long the movie runs
     */
    public String getRuntime(){return runtime;}

    /**
     * getter for the actors
     * @return the main actors
     */
    public String getMainActor(){return mainActor;}

    /**
     * getter for the movie plot
     * @return the plot of the movie
     */
    public String getPlot(){return plot;}

    /**
     * getter for the id
     * @return id
     */
    public String getImdbID(){return imdbID;}

    /**
     * getter url for the poster
     * @return the poster url
     */
    public String getPosterUrl(){return posterUrl;}

    /**
     * getter the title of the movie
     * @return movie title
     */
    public String getMovieTitle(){return movieTitle;}

    /**
     * getter for the year
     * @return the release date of the year
     */
    public String getYear(){return year;}

}
