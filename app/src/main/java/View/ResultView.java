package View;

/**
 * @author Ece Selin Yorgancilar
 * DTO for search result of movie
 */
public class ResultView {

    protected String mTitle;
    protected  String mYear;
    protected String imdbID;
    protected String pUrl;

    /**
     * empty constructor
     */
    public ResultView(){}

    /**
     * constructor for the information variables that will be displayed when the
     * search button is clicked.
     * @param mTitle title of the movie
     * @param mYear release date of the movie
     * @param imdbID id of the movie
     * @param pUrl url of the poster
     */
    public ResultView(String mTitle, String mYear, String imdbID, String pUrl){
        this.mTitle=mTitle;
        this.mYear=mYear;
        this.imdbID=imdbID;
        this.pUrl=pUrl;
    }

    /**
     * getter for the title of the movie
     * @return
     */
    public String getMovieTitle() {
        return mTitle;
    }

    /**
     * getter for the id
     * @return id database
     */
    public String getImdbID(){return imdbID;}

    /**
     * getter for the url of the poster
     * @return url of the poster
     */
    public String getPosterUrl(){return pUrl;}

    /**
     * getter of the release date of the movie
     * @return movie release date
     */
    public String getMovieYear() {
        return mYear;
    }

}
