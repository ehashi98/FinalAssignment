package algonquin.cst2335.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.databinding.PexelsActivityBinding;

public class PexelsSavedActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    PexelsActivityBinding binding;

    ArrayList<PexelsSaved> pexelsSaved;

    PexelsSavedAdapter adapter;

    PexelsViewModel pexelsModel;

    PexelsSavedDAO savedDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = PexelsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pexelsModel = new ViewModelProvider(this).get(PexelsViewModel.class);
        pexelsSaved = pexelsModel.pexelsSaved.getValue();

        recyclerView = binding.recycleView;
        if (pexelsSaved == null) {
            pexelsModel.pexelsSaved.postValue(pexelsSaved = new ArrayList<>());
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new PexelsSavedAdapter(getApplicationContext(), pexelsSaved);
        recyclerView.setAdapter(adapter);
    }

}
