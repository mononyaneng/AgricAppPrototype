package com.example.myapplication5;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication5.ui.main.SectionsPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    CardView createAcc;

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());

        /*Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/

        /*SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);*/

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,myToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();*/

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CreateAdds()).commit();

            navigationView.setCheckedItem(R.id.nav_creare_adds);
        }*/

        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    /*case R.id.bottom_more:
                        View view = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_more, null);

                        BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
                        dialog.setContentView(view);
                        dialog.show();
                        break;*/
                    case R.id.bottom_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.bottom_cart:
                        fragment = new CartFragment();
                        break;
                    case R.id.bottom_user_profile:
                        fragment = new UserProfileFragment();
                        break;
                    case R.id.bottom_chat_room:
                        fragment = new ChatRoomFragment();
                        break;
                    case R.id.bottom_history:
                        fragment = new HistoryFragment();
                        break;

                }
                if (fragment != null){
                    return loadFragment(fragment);
                }
                return true;
            }
        });


        /*createAcc = findViewById(R.id.vv);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(MainActivity.this,CreateAccountActivity.class);
                startActivity(inten);
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        // Get the SearchView and set the searchable configuration
        /*SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget
                .SearchView) menu.findItem(R.id.search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help_center:

                return true;
            case R.id.action_settings:

                return true;
            case R.id.action_sign_out:

                SweetAlertDialog alertDialogBuilder;
                alertDialogBuilder = new SweetAlertDialog(MainActivity.this,SweetAlertDialog.WARNING_TYPE);
                alertDialogBuilder.setTitleText("Sing Out?");

                alertDialogBuilder
                        //.setCancelable(false)
                        .setConfirmText("Yes")
                        .setContentText("Are you sure you want to Sign Out?")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                FirebaseAuth.getInstance().signOut();
                                sDialog.dismissWithAnimation();
                                finish();
                            }
                        })
                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        }).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_creare_adds:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CreateAdds());
                break;
            case R.id.nav_help_center:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HelpCenter());
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.fragment_container,fragment).
                    commit();
            return true;
        }

        return false;
    }
}