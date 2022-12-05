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

public class PexelsAdapter extends RecyclerView.Adapter<PexelsAdapter.RecyclerViewHolder> {
    LayoutInflater inflater;
    ArrayList<PexelsModel> pexelsResults;

    public PexelsAdapter(Context context, ArrayList<PexelsModel> pexelsResults) {
        this.inflater = LayoutInflater.from(context);
        this.pexelsResults = pexelsResults;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ResultsActivityBinding binding = ResultsActivityBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new RecyclerViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.photographer.setText(pexelsResults.get(position).getPhotographer());
        Picasso.get().load(pexelsResults.get(position).getImgThumbnail()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return pexelsResults.size();
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
