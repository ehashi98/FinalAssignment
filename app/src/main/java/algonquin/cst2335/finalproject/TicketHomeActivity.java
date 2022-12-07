package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import algonquin.cst2335.finalproject.databinding.ActivityTickethomeBinding;


/**
 * @author kamelia
 * @versiom 1.0
 */
public class TicketHomeActivity extends NavigationDrawer {

    ActivityTickethomeBinding activityTickethomeBinding;

    @Override
    /**
     * On create method is use the set activity titile.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTickethomeBinding = ActivityTickethomeBinding.inflate(getLayoutInflater());
        setActivityTitle("Homepage");
        setContentView(activityTickethomeBinding.getRoot());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    /**
     * This is create the menu option.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(android.R.menu., menu);
        return true;
    }

    @Override
    /**
     * This will select the menu item.
     */
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
//
        }

        return true;
    }
}