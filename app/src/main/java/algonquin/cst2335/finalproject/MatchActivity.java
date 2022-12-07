package algonquin.cst2335.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

public class MatchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass> teamList;
    Adapter adapter;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        login = findViewById(R.id.login);

        initData();
        initRecyclerView();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MatchActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent intent = new Intent(MatchActivity.this, MatchActivity.class);
                startActivity(intent);
                return true;

            case R.id.item2:
                Intent intent2 = new Intent(MatchActivity.this, fragmentActivityPage.class);
                startActivity(intent2);
                return true;

            case R.id.item3:
                Intent intent3 = new Intent(MatchActivity.this, helpActivity.class);
                startActivity(intent3);
                return true;

            case R.id.item4:
                Intent intent4 = new Intent(MatchActivity.this, volleyActivity.class);
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void initData() {

        teamList = new ArrayList<>();
        teamList.add(new ModelClass(R.drawable.realmadrid, "Real Madrid C.F.","Real Madrid C.F.", "--------------------------------------------------------------------------------"));
        teamList.add(new ModelClass(R.drawable.milan, "AC Milan","AC Milan", "--------------------------------------------------------------------------------"));
        teamList.add(new ModelClass(R.drawable.liverpool, "Liverpool F.C.","Liverpool F.C.", "--------------------------------------------------------------------------------"));
        teamList.add(new ModelClass(R.drawable.bayernmunchen, "F.C. Bayern Munich","F.C. Bayern Munich", "--------------------------------------------------------------------------------"));
        teamList.add(new ModelClass(R.drawable.ajax, "AFC Ajax","AFC Ajax", "--------------------------------------------------------------------------------"));
        teamList.add(new ModelClass(R.drawable.barcelona, "FC Barcelona","FC Barcelona", "--------------------------------------------------------------------------------"));
        teamList.add(new ModelClass(R.drawable.juventus, "Juventus F.C.","Juventus F.C.", "--------------------------------------------------------------------------------"));
        teamList.add(new ModelClass(R.drawable.benfica, "S.L. Benfica","S.L. Benfica", "--------------------------------------------------------------------------------"));
        teamList.add(new ModelClass(R.drawable.manchesterunited, "Manchester United F.C.","Manchester United F.C.", "--------------------------------------------------------------------------------"));

    }

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