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

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CounterfeitDetection1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static EditText resultsearcheview2;
    private FirebaseAuth Cfauth;
    FirebaseFirestore Cfirestore;
    RecyclerView mrecyclerview2;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar3;
    DocumentReference Cdr;
    TextView no, yes;
    private MedicineRecyclerAdapter2 mAdapter;
    private static final String TAG = "CounterfeitDetection1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counterfeit_detection1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Cfirestore = FirebaseFirestore.getInstance();
        Cfauth = FirebaseAuth.getInstance();

        final FirebaseUser users = Cfauth.getCurrentUser();
        String finaluser = users.getEmail();
        String resultemail = finaluser.replace(".", "");

        no = findViewById(R.id.notxt);
        yes = findViewById(R.id.yestxt);

        Cdr = Cfirestore.collection("Medicine").document(Cfauth.getUid());

        drawerLayout = findViewById(R.id.counterfeitdetection1_drawer);
        navigationView = findViewById(R.id.nav_view);
        toolbar3 = findViewById(R.id.sidemenu3);
        resultsearcheview2 = findViewById(R.id.searchfield2);

        mrecyclerview2 = findViewById(R.id.recyclerview2);
        mrecyclerview2.setLayoutManager(new LinearLayoutManager(this));

        //toolbar
        setSupportActionBar(toolbar3);

        //navigation drawer
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar3, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.navcounterfeitdetection);

        Query query = Cfirestore.collection("Medicine")
                .orderBy("medicinename", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Medicine> options = new FirestoreRecyclerOptions.Builder<Medicine>().setQuery(query, Medicine.class).build();
        mAdapter = new MedicineRecyclerAdapter2(options);
        mrecyclerview2.setAdapter(mAdapter);

        resultsearcheview2.addTextChangedListener(new TextWatcher() {
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
                    Query query = Cfirestore.collection("Medicine")
                            .whereEqualTo("malnumber", s.toString())
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
                Intent home = new Intent(CounterfeitDetection1.this, UserDashboard.class);
                startActivity(home);
                break;
            case R.id.navhalaldetection:
                Intent halal = new Intent(CounterfeitDetection1.this, HalalDetection1.class);
                startActivity(halal);
                break;
            case R.id.navcounterfeitdetection:
                Intent counterfiet = new Intent(CounterfeitDetection1.this, CounterfeitDetection1.class);
                startActivity(counterfiet);
                break;
            case R.id.navreportform:
                Intent report = new Intent(CounterfeitDetection1.this, ReportForm1.class);
                startActivity(report);
                break;
            case R.id.navlogout:
                Cfauth.signOut();
                finish();
                Intent logout = new Intent(CounterfeitDetection1.this, LoginRegisterOption.class);
                startActivity(logout);
                finish();
                Toast.makeText(CounterfeitDetection1.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}