package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addActivity extends AppCompatActivity {

    /**
     * Parameters
     */
    EditText nameInput, teamInput, positionInput;
    Button addButton, addPlayerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameInput = findViewById(R.id.playerName);
        teamInput = findViewById(R.id.playerTeam);
        positionInput = findViewById(R.id.playerPosition);
        addButton = findViewById(R.id.addButton);
        addPlayerButton = findViewById(R.id.addPlayerButton);

        /**
         * This button is for adding new objects to the team database.
         */
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabase myDB = new MyDatabase(addActivity.this);
                myDB.addPlayer(nameInput.getText().toString().trim(),
                        teamInput.getText().toString().trim(),
                        positionInput.getText().toString().trim());
            }
        });

        /**
         * This button is for returning to the databaseActivity layout.
         */
        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addActivity.this, databaseActivity.class);
                startActivity(intent);
            }
        });

    }
}