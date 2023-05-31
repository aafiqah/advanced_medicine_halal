package com.example.medicinehalal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageReport1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar8;
    private FirebaseAuth MRfauth;

    RecyclerView MRrecyclerview;
    ReportRecyclerAdapter adapter;
    DatabaseReference mDatabaseref;

    private List<Report> mreports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_report1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        drawerLayout = findViewById(R.id.managereportboard_drawer);
        navigationView = findViewById(R.id.nav_view);
        toolbar8 = findViewById(R.id.sidemenu8);
        //toolbar
        setSupportActionBar(toolbar8);

        MRfauth = FirebaseAuth.getInstance();

        //navigation drawer
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar8,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.navmanagereportform);

        MRrecyclerview = findViewById(R.id.recyclerview5);
        MRrecyclerview.setHasFixedSize(true);
        MRrecyclerview.setLayoutManager(new LinearLayoutManager(this));

        mreports=new ArrayList<>();

        mDatabaseref=FirebaseDatabase.getInstance().getReference("Report");
        mDatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot:snapshot.getChildren())
                {
                    Report report=postSnapshot.getValue(Report.class);
                    mreports.add(report);
                }
                adapter=new ReportRecyclerAdapter(ManageReport1.this, mreports);
                MRrecyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ManageReport1.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.navhome2:
                Intent home = new Intent(ManageReport1.this, AdminDashboard.class);
                startActivity(home);
                break;
            case R.id.navmanageuser:
                Intent user = new Intent(ManageReport1.this,ManageUser1.class);
                startActivity(user);
                break;
            case R.id.navmanagemedicine:
                Intent counterfeit = new Intent(ManageReport1.this,ManageMedicine1.class);
                startActivity(counterfeit);
                break;
            case R.id.navmanagereportform:
                Intent report = new Intent(ManageReport1.this,ManageReport1.class);
                startActivity(report);
                break;
            case R.id.navlogout2:
                MRfauth.signOut();
                finish();
                Intent logout = new Intent(ManageReport1.this, LoginRegisterOption.class);
                startActivity(logout);
                finish();
                Toast.makeText(ManageReport1.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}