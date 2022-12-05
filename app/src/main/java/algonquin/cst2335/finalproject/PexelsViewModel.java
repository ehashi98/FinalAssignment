package algonquin.cst2335.finalproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class PexelsViewModel extends ViewModel {

    MutableLiveData<ArrayList<PexelsModel>> pexelsResults = new MutableLiveData<>();
  //  ArrayList<PexelsModel> selectedImage = new  MutableLiveData<>();

    public PexelsViewModel() {

    }
}
