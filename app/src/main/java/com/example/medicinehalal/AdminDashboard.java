package com.example.medicinehalal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar5;
    CardView user, medicine, report;
    private FirebaseAuth Afauth;
    TextView firebasenameview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        drawerLayout = findViewById(R.id.adminboard_drawer);
        navigationView = findViewById(R.id.nav_view);
        toolbar5 = findViewById(R.id.sidemenu5);
        user = findViewById(R.id.manageusercard);
        medicine = findViewById(R.id.managemedicinecard);
        report = findViewById(R.id.managereportcard);
        firebasenameview = findViewById(R.id.firebasename);

        Afauth = FirebaseAuth.getInstance();
        final FirebaseUser users = Afauth.getCurrentUser();
        String finaluser = users.getEmail();
        String result = finaluser.substring(0, finaluser.indexOf("@"));
        String resultemail = result.replace(".","");
        firebasenameview.setText("Welcome, "+resultemail);

        //toolbar
        setSupportActionBar(toolbar5);

        //navigation drawer
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar5,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.navhome2);

        user.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent userintent = new Intent(AdminDashboard.this, ManageUser1.class);
                startActivity(userintent);
            }
        });

        medicine.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent medicineintent = new Intent(AdminDashboard.this, ManageMedicine1.class);
                startActivity(medicineintent);
            }
        });

        report.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent reportintent = new Intent(AdminDashboard.this, ManageReport1.class);
                startActivity(reportintent);
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
                break;
            case R.id.navmanageuser:
                Intent halal = new Intent(AdminDashboard.this, ManageUser1.class);
                startActivity(halal);
                break;
            case R.id.navmanagemedicine:
                Intent counterfeit = new Intent(AdminDashboard.this,ManageMedicine1.class);
                startActivity(counterfeit);
                break;
            case R.id.navmanagereportform:
                Intent report = new Intent(AdminDashboard.this,ManageReport1.class);
                startActivity(report);
                break;
            case R.id.navlogout2:
                Afauth.signOut();
                finish();
                Intent logout = new Intent(AdminDashboard.this, LoginRegisterOption.class);
                startActivity(logout);
                finish();
                Toast.makeText(AdminDashboard.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}