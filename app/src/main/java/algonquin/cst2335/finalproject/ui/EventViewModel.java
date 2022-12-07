package algonquin.cst2335.finalproject.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.data.Event;


/**
 * This is the EventViewModel class which extend the ViewModel.
 * @author kamelia
 * @version 1.0
 */
public class EventViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Event>> events = new MutableLiveData<>();
    public MutableLiveData<Event> selectedEvent = new MutableLiveData<>();
}