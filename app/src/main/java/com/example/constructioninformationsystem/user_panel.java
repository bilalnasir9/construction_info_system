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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class user_panel extends AppCompatActivity {
    TextView tvname, tvemail;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String name, email,userid;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    NavigationView nav_view;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);
        toolbar = findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);
        nav_view = findViewById(R.id.nav_view_user);
        drawerLayout = findViewById(R.id.user_drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.opendrawer, R.string.closedrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        View headerView = nav_view.inflateHeaderView(R.layout.user_header);
        tvname = headerView.findViewById(R.id.tvheader_username);
        tvemail = headerView.findViewById(R.id.tvheader_useremail);
        getuserdata();
        if (savedInstanceState == null) {
            userid= Objects.requireNonNull(auth.getCurrentUser()).getUid();
            Bundle bundle=new Bundle();
            user_project_fragment fragment=new user_project_fragment();
            bundle.putString("userid",userid);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
        }

        nav_view.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.user_myproject:
                    Bundle bundle=new Bundle();
                    user_project_fragment fragment=new user_project_fragment();
                    bundle.putString("userid",userid);
                    fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.user_materialinfo:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new user_material_usage()).commit();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.user_workersinfo:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new workerinfo()).commit();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.user_logout:
                    startActivity(new Intent(this,MainActivity.class));
                    drawerLayout.closeDrawers();
                    break;
            }
            return true;
        });


    }
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            AlertDialog.Builder builder=new AlertDialog.Builder(this).setMessage("Are you sure to want exit?")
                    .setPositiveButton("Yes",(dialogInterface, i) -> {
                        super.onBackPressed();
                    }).setNegativeButton("No",(dialogInterface, i) -> {
                    });
            builder.show();
        }

    }
    public void getuserdata() {
        reference.child("users").child(Objects.requireNonNull(Objects.requireNonNull(auth.getCurrentUser()).getUid())).child("name").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                try {

                    email = Objects.requireNonNull(auth.getCurrentUser()).getEmail();
                    name = Objects.requireNonNull(dataSnapshot.getValue()).toString();
                    userid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                    tvname.setText(name);
                    tvemail.setText(email);

                } catch (Exception e) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(user_panel.this)
                  .setTitle("Error found") .setMessage("Something went wrong \nYour data is deleted by contractor")
                            .setNegativeButton("OK",(dialogInterface, i) -> {
                            });
                            builder.show();
                }
            }
        });
    }
}
