package algonquin.cst2335.finalproject;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.allmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())  {
            case R.id.pexel:
                Intent pexel = new Intent( MainActivity.this, Pexel.class );
                startActivity( pexel );
                break;
            case R.id.movie:
                Intent movie=new Intent(MainActivity.this,MovieI.class);
                startActivity(movie);

                break;
            case R.id.soccer:
                Intent soccer = new Intent( MainActivity.this, Soccer.class );
                startActivity( soccer );

                break;
            case R.id.ticket:
                Toast.makeText(this, "TICKET MASTER", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(this.toolbar);

        Toolbar supportToolbar = (Toolbar)findViewById(R.id.home_toolbar);
        setSupportActionBar(supportToolbar);

    }





}