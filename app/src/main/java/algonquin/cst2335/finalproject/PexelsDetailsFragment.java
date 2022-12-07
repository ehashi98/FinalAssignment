package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.bumptech.glide.Glide;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.databinding.PexelsFragmentBinding;

public class PexelsDetailsFragment extends Fragment {

    PexelsModel pexelsImage;
    PexelsSavedDAO pDAO;

    public PexelsDetailsFragment(PexelsModel img) {
        pexelsImage = img;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        PexelsFragmentBinding binding = PexelsFragmentBinding.inflate(inflater);

        PexelsDatabase db = Room.databaseBuilder(requireContext(), PexelsDatabase.class, "PexelsDatabase").build();
        pDAO = db.psDao();

        binding.imgUrl.setText(pexelsImage.getUrl());
        binding.imgWidth.setText(String.valueOf(pexelsImage.getWidth()));
        binding.imgHeight.setText(String.valueOf(pexelsImage.getHeight()));
        Glide.with(requireContext()).load(pexelsImage.getOriginalImg()).into(binding.selectedImg);

        Button saveBtn = binding.saveImg;
        saveBtn.setOnClickListener(v -> {
            try {
                saveBtn.setEnabled(false);
                Toast.makeText(requireContext(), "Image saved" + pexelsImage.getId(), Toast.LENGTH_SHORT).show();
                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(() -> pDAO.saveImage(pexelsImage));
            } catch (Exception e) {
                Log.d("test", "image already saved" );
            }

        });

        return binding.getRoot();
    }
}
