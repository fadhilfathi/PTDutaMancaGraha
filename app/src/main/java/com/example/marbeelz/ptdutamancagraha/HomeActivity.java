package com.example.marbeelz.ptdutamancagraha;

//import android.support.v7.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView nav_admin;
    private DrawerLayout drawer;
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_admin);
        MaterialSearchView searchView;
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        coordinatorLayout = findViewById(R.id.editFragment);
        View headerView = navigationView.getHeaderView(0);
        TextView username = (TextView) headerView.findViewById(R.id.nav_admin_nama);
        nav_admin = findViewById(R.id.nav_admin_nama);
        SharedPreferences sharedPreferences = getSharedPreferences("currentlogin", 0);
        String currentlogin = sharedPreferences.getString("logincurrent", "");
        username.setText(currentlogin);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new DaftarRumah_admin(),"DaftarRumah_admin").commit();
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showSnackbar() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Perubahan disimpan!", Snackbar.LENGTH_LONG);
        snackbar.show();}

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DaftarRumah_admin(),"DaftarRumah_admin").addToBackStack(null).commit();
                break;
            case R.id.nav_2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TambahRumah_admin()).addToBackStack(null).commit();
                break;
            case R.id.nav_3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new User_admin()).addToBackStack(null).commit();
                break;
            case R.id.nav_2_1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DaftarBooking_admin()).addToBackStack(null).commit();
                break;
            case R.id.nav_2_2:
                logoutConfirm();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void logoutConfirm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah anda yakin untuk keluar?").setCancelable(true)
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getSharedPreferences("currentlogin", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        editor.commit();
                        Intent x = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(x);
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
        ;
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void switchContent(int fragment_container, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(fragment_container,fragment);
        ft.addToBackStack(null);
        ft.commit();

    }
}
