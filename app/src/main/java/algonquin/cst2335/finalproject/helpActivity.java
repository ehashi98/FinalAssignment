package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class helpActivity extends AppCompatActivity {

    TextView textView1, textView2, textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        textView1 = findViewById(R.id.textViewSigninHelp1);
        textView2 = findViewById(R.id.textViewSigninHelp2);
        textView3 = findViewById(R.id.textViewSigninHelp3);

    }
}
