package algonquin.cst2335.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList columnID, playerName, playerTeam, playerPosition;

    DataAdapter(Activity activity, Context context, ArrayList columnID, ArrayList playerName, ArrayList playerTeam,
                ArrayList playerPosition){
        this.activity = activity;
        this.context = context;
        this.columnID = columnID;
        this.playerName = playerName;
        this.playerTeam = playerTeam;
        this.playerPosition = playerPosition;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.playerIDText.setText(String.valueOf(columnID.get(position)));
        holder.playerNameText.setText(String.valueOf(playerName.get(position)));
        holder.playerTeamText.setText(String.valueOf(playerTeam.get(position)));
        holder.playerPositionText.setText(String.valueOf(playerPosition.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, updateActivity.class);
                intent.putExtra("ID", String.valueOf(columnID.get(position)));
                intent.putExtra("Name", String.valueOf(playerName.get(position)));
                intent.putExtra("Team", String.valueOf(playerTeam.get(position)));
                intent.putExtra("Position", String.valueOf(playerPosition.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return columnID.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView playerIDText, playerNameText, playerTeamText, playerPositionText;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            playerIDText = itemView.findViewById(R.id.playerIDText);
            playerNameText = itemView.findViewById(R.id.playerNameText);
            playerTeamText = itemView.findViewById(R.id.playerTeamText);
            playerPositionText = itemView.findViewById(R.id.playerPositionText);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
