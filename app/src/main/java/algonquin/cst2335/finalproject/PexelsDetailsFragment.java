package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

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

        PexelsDatabase db = Room.databaseBuilder(getContext(), PexelsDatabase.class, "PexelsDatabase").build();
        pDAO = db.psDao();

        binding.imgUrl.setText(pexelsImage.getUrl());
        binding.imgWidth.setText(String.valueOf(pexelsImage.getWidth()));
        binding.imgHeight.setText(String.valueOf(pexelsImage.getHeight()));
        //try {
            Glide.with(getContext()).load(pexelsImage.getOriginalImg()).into(binding.selectedImg);
            /*Picasso.get()
                    .load(pexelsImage.getOriginalImg())
                    .into(binding.selectedImg);
        } catch (RuntimeException e) {
            Log.d("test", "image too big being shrunk");
            Picasso.get().load(pexelsImage.getImgThumbnail()).into(binding.selectedImg);
        }*/

       /* TextView imgUrl = binding.imgUrl;
        imgUrl.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(String.valueOf(binding.imgUrl)));
            startActivity(intent);
        });*/

        Button saveBtn = binding.saveImg;
        saveBtn.setOnClickListener(v -> {
            try {
                Toast.makeText(getContext(), "Image saved", Toast.LENGTH_SHORT).show();
                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(() -> {
                    pDAO.saveImage(pexelsImage);
                });
            } catch (Exception e) {
                Log.d("test", "image already saved" );
            }

        });

        return binding.getRoot();
    }
}
