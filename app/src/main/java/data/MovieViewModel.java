package data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import Database.MovieInfo;
import View.ResultView;

/**
 * @author Ece Selin Yorgancilar
 * This ViewModel class is to get and
 * keep the information that is necessary for an Activity or a Fragment.
 * Also to survive the rotation changes.
 */
public class MovieViewModel extends ViewModel {
    /**
     * for the selected object
     */
    public MutableLiveData<MovieInfo> selected = new MutableLiveData<MovieInfo>();

    /**
     * for the result objects array
     */
    public  MutableLiveData<ArrayList<ResultView>> result=new MutableLiveData<>();

    /**
     * for the movie information list array
     */
    public MutableLiveData<ArrayList<MovieInfo>> List=new MutableLiveData<>();
}
