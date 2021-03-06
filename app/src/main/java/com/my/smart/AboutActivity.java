package com.my.smart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AboutActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        mAuth = FirebaseAuth.getInstance();


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Menu menu = navigationView.getMenu();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            menu.findItem(R.id.nav_login).setVisible(false);
        }
        if (user == null) {
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(false);

        }

        menu.findItem(R.id.nav_about).setVisible(false);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(AboutActivity.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_profile);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                startActivity(new Intent(AboutActivity.this, UserProfileActivity.class));
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                startActivity(new Intent(AboutActivity.this, WelcomeActivity.class));
                finish();
                break;
            case R.id.nav_about:
                startActivity(new Intent(AboutActivity.this, AboutActivity.class));
                break;
            case R.id.nav_login:
                startActivity(new Intent(AboutActivity.this, LoginActivity.class));
                break;
            case R.id.nav_contact:
                startActivity(new Intent(AboutActivity.this, AboutActivity.class));
                break;
            case R.id.nav_dashboard:
                startActivity(new Intent(AboutActivity.this, WelcomeActivity.class));
                break;
            case R.id.nav_share:
                startActivity(new Intent(AboutActivity.this, WelcomeActivity.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}