package com.example.constructioninformationsystem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class admin_panel extends AppCompatActivity {
    TextView tvemail, tvname;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String email;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    NavigationView nav_view;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        toolbar = findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);
        nav_view = findViewById(R.id.nav_view_admin);
        drawerLayout = findViewById(R.id.admin_drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.opendrawer, R.string.closedrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new admin_project_fragment()).commit();
        }
        View headerView = nav_view.inflateHeaderView(R.layout.user_header);
        tvemail = headerView.findViewById(R.id.tvheader_useremail);
        tvname = headerView.findViewById(R.id.tvheader_username);
        try {
            tvemail.setText(Objects.requireNonNull(auth.getCurrentUser()).getEmail());
        } catch (Exception exception) {
            Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show();
        }
        tvname.setText("Admin account");
        nav_view.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.admin_project:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new admin_project_fragment()).commit();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.admin_allusers:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new admin_view_user()).commit();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.admin_logout:
                    startActivity(new Intent(admin_panel.this,MainActivity.class));
                    drawerLayout.closeDrawers();
                    break;
            }
            return true;
        });

    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage("Are you sure to want exit?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        super.onBackPressed();
                    }).setNegativeButton("No", (dialogInterface, i) -> {
                    });
            builder.show();
        }
    }
}