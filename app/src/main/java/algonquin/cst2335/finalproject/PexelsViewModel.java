package algonquin.cst2335.finalproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class PexelsViewModel extends ViewModel {

    MutableLiveData<ArrayList<PexelsModel>> pexelsResults = new MutableLiveData<>();
    MutableLiveData<ArrayList<PexelsModel>> pexelsSaved = new MutableLiveData<>();
    MutableLiveData<PexelsModel> pexelsSelected = new MutableLiveData<>();

    public PexelsViewModel() {

    }
}
