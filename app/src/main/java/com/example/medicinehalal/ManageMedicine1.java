package com.example.medicinehalal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ManageMedicine1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static EditText resultsearcheview4;
    Button addmedicine;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar7;
    private FirebaseAuth MMfauth;
    RecyclerView MMrecyclerview;
    FirebaseFirestore fstore5;
    private MedicineRecyclerAdapter3 MMAdapter;
    private static final String TAG = "ManageMedicine1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_medicine1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        drawerLayout = findViewById(R.id.managemedicineboard_drawer);
        navigationView = findViewById(R.id.nav_view);
        toolbar7 = findViewById(R.id.sidemenu7);
        resultsearcheview4= findViewById(R.id.searchmedicinefield);
        addmedicine = findViewById(R.id.addmedicine_btn);

        MMfauth = FirebaseAuth.getInstance();
        fstore5 = FirebaseFirestore.getInstance();

        //toolbar
        setSupportActionBar(toolbar7);

        //navigation drawer
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar7,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.navmanagemedicine);

        MMrecyclerview = findViewById(R.id.recyclerview4);
        MMrecyclerview.setLayoutManager(new LinearLayoutManager(this));

        Query query = fstore5.collection("Medicine")
                .orderBy("medicinename", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Medicine> options = new FirestoreRecyclerOptions.Builder<Medicine>()
                .setQuery(query, Medicine.class)
                .build();
        MMAdapter = new MedicineRecyclerAdapter3(options);
        MMrecyclerview.setAdapter(MMAdapter);

        resultsearcheview4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence2, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "Searchbox has changed to: " + s.toString());

                Query query;
                if (s.toString().isEmpty()) {
                    query = fstore5.collection("Medicine")
                            .orderBy("medicinename", Query.Direction.ASCENDING);
                } else {
                    query = fstore5.collection("Medicine")
                            .whereEqualTo("medicinename", s.toString())
                            .orderBy("medicinename", Query.Direction.ASCENDING);
                }
                FirestoreRecyclerOptions<Medicine> options = new FirestoreRecyclerOptions.Builder<Medicine>()
                        .setQuery(query, Medicine.class)
                        .build();
                MMAdapter.updateOptions(options);
            }
        });

        addmedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddMedicineData.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        MMAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (MMAdapter != null) {
            MMAdapter.stopListening();
        }
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
                Intent home = new Intent(ManageMedicine1.this, AdminDashboard.class);
                startActivity(home);
                break;
            case R.id.navmanageuser:
                Intent user = new Intent(ManageMedicine1.this,ManageUser1.class);
                startActivity(user);
                break;
            case R.id.navmanagemedicine:
                Intent counterfeit = new Intent(ManageMedicine1.this,ManageMedicine1.class);
                startActivity(counterfeit);
                break;
            case R.id.navmanagereportform:
                Intent report = new Intent(ManageMedicine1.this,ManageReport1.class);
                startActivity(report);
                break;
            case R.id.navlogout2:
                MMfauth.signOut();
                finish();
                Intent logout = new Intent(ManageMedicine1.this, LoginRegisterOption.class);
                startActivity(logout);
                finish();
                Toast.makeText(ManageMedicine1.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}