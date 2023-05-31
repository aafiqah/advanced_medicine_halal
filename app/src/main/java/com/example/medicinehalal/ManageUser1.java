package com.example.medicinehalal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.BufferedReader;
import java.util.List;

public class ManageUser1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static EditText resultsearcheview3;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar6;
    private FirebaseAuth MUfauth;
    FirebaseFirestore MUfirestore;
    RecyclerView MUrecyclerview;
    DocumentReference MUdr;
    private UserRecyclerAdapter MUAdapter;
    private static final String TAG = "ManageUser1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        drawerLayout = findViewById(R.id.manageuserboard_drawer);
        navigationView = findViewById(R.id.nav_view);
        toolbar6 = findViewById(R.id.sidemenu6);
        resultsearcheview3= findViewById(R.id.searchuserfield);

        MUfauth = FirebaseAuth.getInstance();
        MUfirestore = FirebaseFirestore.getInstance();
        MUdr = MUfirestore.collection("Users").document(MUfauth.getUid());

        //toolbar
        setSupportActionBar(toolbar6);

        //navigation drawer
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar6,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.navmanageuser);

        MUrecyclerview = findViewById(R.id.recyclerview3);
        MUrecyclerview.setLayoutManager(new LinearLayoutManager(this));

        Query query = MUfirestore.collection("Users")
                .orderBy("fullname", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();
        MUAdapter = new UserRecyclerAdapter(options);
        MUrecyclerview.setAdapter(MUAdapter);

        resultsearcheview3.addTextChangedListener(new TextWatcher() {
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
                    query = MUfirestore.collection("Users")
                            .orderBy("fullname", Query.Direction.ASCENDING);
                } else {
                    query = MUfirestore.collection("Users")
                            .whereEqualTo("role", s.toString())
                            .orderBy("fullname", Query.Direction.ASCENDING);
                }
                FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                        .setQuery(query, User.class)
                        .build();
                MUAdapter.updateOptions(options);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        MUAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (MUAdapter != null) {
            MUAdapter.stopListening();
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
                Intent home = new Intent(ManageUser1.this, AdminDashboard.class);
                startActivity(home);
                break;
            case R.id.navmanageuser:
                break;
            case R.id.navmanagemedicine:
                Intent counterfeit = new Intent(ManageUser1.this,ManageMedicine1.class);
                startActivity(counterfeit);
                break;
            case R.id.navmanagereportform:
                Intent report = new Intent(ManageUser1.this,ManageReport1.class);
                startActivity(report);
                break;
            case R.id.navlogout2:
                MUfauth.signOut();
                finish();
                Intent logout = new Intent(ManageUser1.this, LoginRegisterOption.class);
                startActivity(logout);
                finish();
                Toast.makeText(ManageUser1.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}