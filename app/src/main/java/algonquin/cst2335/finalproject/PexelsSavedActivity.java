package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.databinding.PexelsSavedBinding;
import algonquin.cst2335.finalproject.databinding.ResultsActivityBinding;
import algonquin.cst2335.finalproject.databinding.SavedActivityBinding;

public class PexelsSavedActivity extends AppCompatActivity {

    PexelsSavedBinding binding;

    ArrayList<PexelsModel> pexelsSaved;

    PexelsViewModel pexelsModel;

    RecyclerView.Adapter<PexelsSavedActivity.RecyclerViewHolder> adapter;

    PexelsSavedDAO pDAO;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = PexelsSavedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pexelsModel = new ViewModelProvider(this).get(PexelsViewModel.class);

        PexelsDatabase db = Room.databaseBuilder(getApplicationContext(), PexelsDatabase.class, "PexelsDatabase").build();
        pDAO = db.psDao();

        if (pexelsSaved == null) {
            pexelsModel.pexelsSaved.postValue(pexelsSaved = new ArrayList<>());
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                pexelsSaved.addAll(pDAO.getAllImages());
                runOnUiThread(() -> binding.recycleView.setAdapter(adapter));
            });
        }

        binding.recycleView.setAdapter(adapter = new RecyclerView.Adapter<RecyclerViewHolder>() {

            @NonNull
            @Override
            public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SavedActivityBinding binding = SavedActivityBinding.inflate(getLayoutInflater());
                return new RecyclerViewHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
                PexelsModel img = pexelsSaved.get(position);
                holder.photographer.setText(img.getPhotographer());
                Picasso.get().load(img.getImgThumbnail()).into(holder.thumbnail);
            }

            @Override
            public int getItemCount() {
                return pexelsSaved.size();
            }

        });

/*        pexelsModel.pexelsSelected.observe(this, (newValue) -> {
            PexelsDetailsFragment pexelsFragment = new PexelsDetailsFragment(newValue);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLocation, pexelsFragment).addToBackStack("").commit();
        });*/

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView photographer;
        ImageView thumbnail;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            /*itemView.setOnClickListener(click -> {
                final int position = getAdapterPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder(PexelsSavedActivity.this);
                PexelsModel img = pexelsSaved.get(position);
                builder.setMessage("Do you want to delete this image : " + img )
                        .setTitle("Question:")
                        .setPositiveButton("Yes", (dialog, cl) -> {
                            Snackbar.make(itemView, "You deleted image #" + position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo", clk ->{
                                        Executor thread = Executors.newSingleThreadExecutor();
                                        thread.execute(() -> {
                                            pDAO.saveImage(img);
                                        });
                                        pexelsModel.pexelsSaved.getValue().add(img);
                                        adapter.notifyItemInserted(position);
                                    })
                                    .show();
                            Executor thread = Executors.newSingleThreadExecutor();
                            thread.execute(() -> {
                                pDAO.deleteImage(img);
                            });
                            adapter.notifyItemRemoved(position);
                            pexelsModel.pexelsSaved.getValue().remove(position);
                        })
                        .setNegativeButton("No", (dialog, cl) -> { })
                        .create()
                        .show();
            });*/

            photographer = itemView.findViewById(R.id.photogName);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }

    }

}
