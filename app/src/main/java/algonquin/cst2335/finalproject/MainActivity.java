package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass> teamList;
    Adapter adapter;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);

        initData();
        initRecyclerView();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

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