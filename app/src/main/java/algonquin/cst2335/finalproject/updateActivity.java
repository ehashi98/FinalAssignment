package algonquin.cst2335.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class updateActivity extends AppCompatActivity {

    /**
     * Parameter
     */
    EditText nameInput, teamInput, positionInput;
    Button updateButton, updatePlayerButton, deleteButton;

    String id, name, team, position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        nameInput = findViewById(R.id.playerName2);
        teamInput = findViewById(R.id.playerTeam2);
        positionInput = findViewById(R.id.playerPosition2);
        updateButton = findViewById(R.id.updateButton);
        updatePlayerButton = findViewById(R.id.updatePlayerButton);
        deleteButton = findViewById(R.id.deleteButton);

        //First we call this
        getAndSetIntentData();

        /**
         * This takes the information that was added,
         * changes it, and updates it with the new information.
         */
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //And only then we call this
                MyDatabase myDB = new MyDatabase(updateActivity.this);
                name = nameInput.getText().toString().trim();
                team = teamInput.getText().toString().trim();
                position = positionInput.getText().toString().trim();
                myDB.updateData(id, name, team, position);
            }
        });

        /**
         * This button takes you back to databaseActivity layout.
         */
        updatePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(updateActivity.this, databaseActivity.class);
                startActivity(intent);
            }
        });

        /**
         * This button deletes the added or updated objects.
         */
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

    }

    /**
     * This gets and sets the data that is going to be updated
     * and displays a toast message.
     */
    void getAndSetIntentData(){
        if(getIntent().hasExtra("ID") && getIntent().hasExtra("Name") &&
                getIntent().hasExtra("Team") && getIntent().hasExtra("Position")){

            //Getting Data from Intent
            id = getIntent().getStringExtra("ID");
            name = getIntent().getStringExtra("Name");
            team = getIntent().getStringExtra("Team");
            position = getIntent().getStringExtra("Position");

            //Setting Intent Data
            nameInput.setText(name);
            teamInput.setText(team);
            positionInput.setText(position);
        }else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This gives you a chance to double check if you want ot
     * delete the object from the database.
     */
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabase myDB = new MyDatabase(updateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
