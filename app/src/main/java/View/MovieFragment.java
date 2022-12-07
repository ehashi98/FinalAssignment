package View;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.io.File;

import Database.MovieInfo;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.MovieDetailsFragmentBinding;

/**
 * @author Ece Selin Yorgancilar
 *  This class extends from Fragment. The purpose is to set the fragment.
 */
public class MovieFragment extends Fragment {
    MovieInfo selectedMovie;
    public MovieFragment(MovieInfo selectedMovie){
        this.selectedMovie=selectedMovie;

    }



    /**
     * Called for the fragment to instantiate its user interface view
     * @param inflater LayoutInflater object
     * @param container ViewGroup object
     * @param savedInstanceState Bundle object
     * @return outermost View in the associated layout file.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        MovieDetailsFragmentBinding binding=MovieDetailsFragmentBinding.inflate(getLayoutInflater());

        String ratingID=selectedMovie.getImdbID();
        String pathName=getContext().getFilesDir()+"/"+ratingID+".jpg";

        File file=new File(pathName);
        if(file.exists()){
            Bitmap image= BitmapFactory.decodeFile(pathName);
            binding.fPoster.setImageBitmap(image);
        }

        binding.fTitle.setText(selectedMovie.getMovieTitle());
        binding.fRuntime.setText(getResources().getString(R.string.runtime)+selectedMovie.getRuntime() + ".");
        binding.fRating.setText(selectedMovie.getRating());
        binding.fReleaseYear.setText(getResources().getString(R.string.released) +selectedMovie.getYear()+ ".");
        binding.fActors.setText(String.format(getResources().getString(R.string.cast)+"  %s",selectedMovie.getMainActor())+".");
        binding.fPlot.setText(String.format(getResources().getString(R.string.plot)+" %s",selectedMovie.getPlot())+".");


        binding.fCloseBtn.setOnClickListener(clk->{
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        });

        return binding.getRoot();


    }


}
