package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import algonquin.cst2335.finalproject.databinding.PexelsFragmentBinding;

public class PexelsDetailsFragment extends Fragment {

    PexelsModel pexelsImage;

    public PexelsDetailsFragment(PexelsModel img) {
        pexelsImage = img;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        PexelsFragmentBinding binding = PexelsFragmentBinding.inflate(inflater);


        binding.imgUrl.setText(pexelsImage.getUrl());
        binding.imgWidth.setText(String.valueOf(pexelsImage.getWidth()));
        binding.imgHeight.setText(String.valueOf(pexelsImage.getHeight()));
        Picasso.get().load(pexelsImage.getOriginalImg()).into(binding.selectedImg);


        TextView imgUrl = binding.imgUrl;
        imgUrl.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(String.valueOf(binding.imgUrl)));
            startActivity(intent);
        });

        return binding.getRoot();
    }
}
