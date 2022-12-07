package algonquin.cst2335.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    /**
    * Parameter
    */
    private List<ModelClass> teamList;

    public Adapter(List<ModelClass>teamList){
        this.teamList = teamList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position){

        int resource = teamList.get(position).getImageView1();
        String name = teamList.get(position).getTextView();
        String buttonViewH = teamList.get(position).getButton();
        String line = teamList.get(position).getDivider();

        holder.setData(resource, name, buttonViewH, line);
    }

    @Override
    public int getItemCount(){
        return teamList.size();
    }

    /**
     * This is for creating the recycler view
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView1;
        private TextView textView;
        private Button button;
        private TextView divider;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            imageView1 = itemView.findViewById(R.id.imageView1);
            textView = itemView.findViewById(R.id.textView);
            button = itemView.findViewById(R.id.button);
            divider = itemView.findViewById(R.id.textViewLine);
        }

        public void setData(int resource, String name, String buttonViewH, String line){
            imageView1.setImageResource(resource);
            textView.setText(name);
            textView.setText(buttonViewH);
            divider.setText(line);

        }


    }
}

