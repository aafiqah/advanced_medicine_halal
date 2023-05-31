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
import android.widget.TextView;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class HalalDetection1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static EditText resultsearcheview;
    private FirebaseAuth Hfauth;
    FirebaseFirestore Hfirestore;
    RecyclerView mrecyclerview;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar2;
    Button barcode;
    TextView no, yes;
    DocumentReference Hdr;
    private MedicineRecyclerAdapter mAdapter;
    private static final String TAG = "HalalDetection1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halal_detection1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Hfirestore = FirebaseFirestore.getInstance();
        Hfauth = FirebaseAuth.getInstance();

        final FirebaseUser users = Hfauth.getCurrentUser();
        String finaluser = users.getEmail();
        String resultemail = finaluser.replace(".", "");

        Hdr = Hfirestore.collection("Medicine").document(Hfauth.getUid());

        drawerLayout = findViewById(R.id.halaldetection1_drawer);
        navigationView = findViewById(R.id.nav_view);
        toolbar2 = findViewById(R.id.sidemenu2);
        barcode = findViewById(R.id.scannerbtn);
        resultsearcheview = findViewById(R.id.searchfield);

        mrecyclerview = findViewById(R.id.recyclerview1);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));

        Query query = Hfirestore.collection("Medicine")
                .orderBy("medicinename", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Medicine> options = new FirestoreRecyclerOptions.Builder<Medicine>()
                .setQuery(query, Medicine.class)
                .build();
        mAdapter = new MedicineRecyclerAdapter(options);
        mrecyclerview.setAdapter(mAdapter);

        //toolbar
        setSupportActionBar(toolbar2);

        no = findViewById(R.id.notxt);
        yes = findViewById(R.id.yestxt);

        //navigation drawer
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar2, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.navhalaldetection);

        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent barodeintent = new Intent(getApplicationContext(), ScanCodeActivitySearch.class);
                startActivity(barodeintent);
            }
        });

        resultsearcheview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    no.setVisibility(View.VISIBLE);
                    yes.setVisibility(View.INVISIBLE);
                }else{
                    no.setVisibility(View.INVISIBLE);
                    yes.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence2, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "Searchbox has changed to: " + s.toString());

                if (s.toString().isEmpty()) {
                    no.setVisibility(View.VISIBLE);
                    yes.setVisibility(View.INVISIBLE);
                } else {
                    no.setVisibility(View.INVISIBLE);
                    yes.setVisibility(View.VISIBLE);
                    Query query = Hfirestore.collection("Medicine")
                            .whereEqualTo("serialnumber", s.toString())
                            .orderBy("medicinename", Query.Direction.ASCENDING);
                    FirestoreRecyclerOptions<Medicine> options = new FirestoreRecyclerOptions.Builder<Medicine>()
                            .setQuery(query, Medicine.class)
                            .build();
                    mAdapter.updateOptions(options);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
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
            case R.id.navhome:
                Intent home = new Intent(HalalDetection1.this, UserDashboard.class);
                startActivity(home);
                break;
            case R.id.navhalaldetection:
                Intent halal = new Intent(HalalDetection1.this, HalalDetection1.class);
                startActivity(halal);
                break;
            case R.id.navcounterfeitdetection:
                Intent counterfeit = new Intent(HalalDetection1.this, CounterfeitDetection1.class);
                startActivity(counterfeit);
                break;
            case R.id.navreportform:
                Intent report = new Intent(HalalDetection1.this, ReportForm1.class);
                startActivity(report);
                break;
            case R.id.navlogout:
                Hfauth.signOut();
                finish();
                Intent logout = new Intent(HalalDetection1.this, LoginRegisterOption.class);
                startActivity(logout);
                finish();
                Toast.makeText(HalalDetection1.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}