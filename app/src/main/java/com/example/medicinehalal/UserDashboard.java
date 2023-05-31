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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar1;
    CardView halal, counterfeit, report;
    private FirebaseAuth Ufauth;
    TextView firebasenameview;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        drawerLayout = findViewById(R.id.userboard_drawer);
        navigationView = findViewById(R.id.nav_view);
        toolbar1 = findViewById(R.id.sidemenu1);
        halal = findViewById(R.id.halaldetectioncard);
        counterfeit = findViewById(R.id.counterfeitdetectioncard);
        report = findViewById(R.id.reportformcard);
        firebasenameview = findViewById(R.id.firebasename);

        Ufauth = FirebaseAuth.getInstance();
        final FirebaseUser users = Ufauth.getCurrentUser();
        String finaluser = users.getEmail();
        String result = finaluser.substring(0, finaluser.indexOf("@"));
        String resultemail = result.replace(".","");
        firebasenameview.setText("Welcome, "+resultemail);

        //toolbar
        setSupportActionBar(toolbar1);

        //navigation drawer
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar1,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.navhome);

        halal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent halalintent = new Intent(UserDashboard.this, HalalDetection1.class);
                startActivity(halalintent);
            }
        });

        counterfeit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent counterintent = new Intent(UserDashboard.this, CounterfeitDetection1.class);
                startActivity(counterintent);
            }
        });

        report.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent reportintent = new Intent(UserDashboard.this, ReportForm1.class);
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
            case R.id.navhome:
                break;
            case R.id.navhalaldetection:
                Intent halal = new Intent(UserDashboard.this,HalalDetection1.class);
                startActivity(halal);
                break;
            case R.id.navcounterfeitdetection:
                Intent counterfeit = new Intent(UserDashboard.this,CounterfeitDetection1.class);
                startActivity(counterfeit);
                break;
            case R.id.navreportform:
                Intent report = new Intent(UserDashboard.this,ReportForm1.class);
                startActivity(report);
                break;
            case R.id.navlogout:
                Ufauth.signOut();
                finish();
                Intent logout = new Intent(UserDashboard.this, LoginRegisterOption.class);
                startActivity(logout);
                finish();
                Toast.makeText(UserDashboard.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}