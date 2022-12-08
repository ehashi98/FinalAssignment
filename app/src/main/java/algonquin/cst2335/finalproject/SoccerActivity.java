package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SoccerActivity extends AppCompatActivity {

    /**
     * Parameter
     */
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass> teamList;
    Adapter adapter;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soccer_main_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        login = findViewById(R.id.login);

        initData();
        initRecyclerView();

        /**
         * This button will take you to the login page.
         */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SoccerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * This creates the drop down menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    /**
     * These are the clickable activities in the menu.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(SoccerActivity.this, SoccerActivity.class);
                startActivity(intent);
                return true;

            case R.id.item2:
                Intent intent2 = new Intent(SoccerActivity.this, fragmentActivityPage.class);
                startActivity(intent2);
                return true;

            case R.id.item3:
                Intent intent3 = new Intent(SoccerActivity.this, helpActivity.class);
                startActivity(intent3);
                return true;

            case R.id.item4:
                Intent intent4 = new Intent(SoccerActivity.this, volleyActivity.class);
                startActivity(intent4);
                return true;

            case R.id.item5:
                Intent intent5 = new Intent(SoccerActivity.this, databaseActivity.class);
                startActivity(intent5);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * This will load the team information to the main layout recycler view.
     */
    private void initData() {

        teamList = new ArrayList<>();
        teamList.add(new ModelClass(R.drawable.realmadrid, "Real Madrid C.F.", "Real Madrid C.F.", "--------------------------------------------------------------------------------"));
        teamList.add(new ModelClass(R.drawable.milan, "AC Milan", "AC Milan", "--------------------------------------------------------------------------------"));
        teamList.add(new ModelClass(R.drawable.liverpool, "Liverpool F.C.", "Liverpool F.C.", "--------------------------------------------------------------------------------"));
        teamList.add(new ModelClass(R.drawable.bayernmunchen, "F.C. Bayern Munich", "F.C. Bayern Munich", "--------------------------------------------------------------------------------"));
        teamList.add(new ModelClass(R.drawable.ajax, "AFC Ajax", "AFC Ajax", "--------------------------------------------------------------------------------"));
        teamList.add(new ModelClass(R.drawable.barcelona, "FC Barcelona", "FC Barcelona", "--------------------------------------------------------------------------------"));
        teamList.add(new ModelClass(R.drawable.juventus, "Juventus F.C.", "Juventus F.C.", "--------------------------------------------------------------------------------"));
        teamList.add(new ModelClass(R.drawable.benfica, "S.L. Benfica", "S.L. Benfica", "--------------------------------------------------------------------------------"));
        teamList.add(new ModelClass(R.drawable.manchesterunited, "Manchester United F.C.", "Manchester United F.C.", "--------------------------------------------------------------------------------"));

    }

    /**
     * This initiates the recycler view.
     */
    private void initRecyclerView() {

        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(teamList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
