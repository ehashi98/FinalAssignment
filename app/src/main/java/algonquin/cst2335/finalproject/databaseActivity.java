package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class databaseActivity extends AppCompatActivity {

    /*
     * Parameters
     */
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDatabase myDB;
    ArrayList<String> columnID, playerName, playerTeam, playerPosition;
    DataAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        recyclerView = findViewById(R.id.database);
        add_button = findViewById(R.id.add_button);

        /*
         * This button takes you to the add layout.
         */
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(databaseActivity.this, addActivity.class);
                startActivity(intent);
            }
        });

        /*
         * This creates a new database and the separate array lists for each variable.
         */
        myDB = new MyDatabase(databaseActivity.this);
        columnID = new ArrayList<>();
        playerName = new ArrayList<>();
        playerTeam = new ArrayList<>();
        playerPosition = new ArrayList<>();

        /*
         * This stores the data.
         */
        storeDataInArrays();

        /*
         * This adds the information to the recycle view.
         */
        dataAdapter = new DataAdapter(databaseActivity.this, this, columnID, playerName, playerTeam, playerPosition);
        recyclerView.setAdapter(dataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(databaseActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    /**
     * This stores the data in the array lists and places them in the
     * correct columns.
     * If there is no data, there is a toast message displayed.
     */
    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                columnID.add(cursor.getString(0));
                playerName.add(cursor.getString(1));
                playerTeam.add(cursor.getString(2));
                playerPosition.add(cursor.getString(3));
            }
        }
    }

}
