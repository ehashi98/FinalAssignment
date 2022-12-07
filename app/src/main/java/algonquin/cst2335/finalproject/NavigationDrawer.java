package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import algonquin.cst2335.finalproject.ui.TicketMasterActivity;


/**
 * This is the NavigationDrawer class which extend the AppCompatActivity and implements the NavigationView.
 * @author
 * @version 1.0
 *
 */
public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    /**
     * setContent view is help to navigate the toolbar.
     */
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_navigation_drawer, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();
    }

    @Override
    /**
     * Using the switch case the menU Item is created.
     */
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch(item.getItemId()) {

            case R.id.homepage:
                startActivity(new Intent(this, TicketHomeActivity.class));
                break;

            case R.id.ticketmaster:
                startActivity(new Intent(this, TicketMasterActivity.class));
                break;


        }

        return false;
    }

    /**
     * Set the action bar using the activtiy title.
     * @param titleString title will be print as the string
     */
    protected void setActivityTitle(String titleString) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleString);
        }
    }


}