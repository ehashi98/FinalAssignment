package algonquin.cst2335.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class loginPageActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        TextView t1;
        t1 = findViewById(R.id.textView);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String email = sp.getString("email", "");

        t1.setText(email);

        button = findViewById(R.id.teampage);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(loginPageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}