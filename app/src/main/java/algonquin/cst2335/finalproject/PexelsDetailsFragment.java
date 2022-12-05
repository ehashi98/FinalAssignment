package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import algonquin.cst2335.finalproject.PexelsModel;
import algonquin.cst2335.finalproject.databinding.PexelsFragmentBinding;

public class PexelsDetailsFragment extends Fragment {
    PexelsModel selected = new PexelsModel();

    public PexelsDetailsFragment(PexelsModel img) {
        selected = img;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        PexelsFragmentBinding binding = PexelsFragmentBinding.inflate(inflater);

        binding.imgHeight.setText(selected.getHeight());
        binding.imgWidth.setText(selected.getWidth());
        binding.imgUrl.setText(selected.getUrl());
        Picasso.get().load(selected.getOriginalImg()).into(binding.selectedImg);

        return binding.getRoot();
    }
}
