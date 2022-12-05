package algonquin.cst2335.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.databinding.ResultsActivityBinding;

public class PexelsSavedAdapter extends RecyclerView.Adapter<PexelsSavedAdapter.RecyclerViewHolder> {
    LayoutInflater inflater;
    ArrayList<PexelsSaved> pexelsSaved;


    public PexelsSavedAdapter(Context context, ArrayList<PexelsSaved> pexelsSaved) {
        this.inflater = LayoutInflater.from(context);
        this.pexelsSaved = pexelsSaved;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ResultsActivityBinding binding = ResultsActivityBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new PexelsSavedAdapter.RecyclerViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.photographer.setText(pexelsSaved.get(position).getPhotographer());
        Picasso.get().load(pexelsSaved.get(position).getImgThumbnail()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView photographer;
        ImageView thumbnail;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            photographer = itemView.findViewById(R.id.photogName);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }

}
