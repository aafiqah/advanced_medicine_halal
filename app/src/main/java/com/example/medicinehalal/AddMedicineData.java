package com.example.medicinehalal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddMedicineData extends AppCompatActivity {

    EditText medicinenamemedicine, serialnumbermedicine, malnumbermedicine, halalstatusmedicine, counterfeitstatusmedicine, manufacturermedicine, activeingmedicine, inactiveingmedicine, haramingmedicine, mushboohingmedicine, medicinepicturemedicine;
    Button submitaddmedicine;
    ImageView addmedicine_back;
    Medicine medicine;
    String medicineid;
    FirebaseFirestore firestore;
    ProgressDialog progressDialog;

    public static final String TAG = "AddMedicineData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine_data);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        medicinenamemedicine = findViewById(R.id.addmedicine_medicine);
        serialnumbermedicine = findViewById(R.id.addmedicine_serialnumber);
        malnumbermedicine = findViewById(R.id.addmedicine_malnumber);
        halalstatusmedicine = findViewById(R.id.addmedicine_halalstatus);
        counterfeitstatusmedicine = findViewById(R.id.addmedicine_counterfeitstatus);
        manufacturermedicine = findViewById(R.id.addmedicine_manufacturer);
        activeingmedicine = findViewById(R.id.addmedicine_activeingredient);
        inactiveingmedicine = findViewById(R.id.addmedicine_inactiveingredient);
        haramingmedicine = findViewById(R.id.addmedicine_haramingredient);
        mushboohingmedicine = findViewById(R.id.addmedicine_mushboohingredient);
        medicinepicturemedicine = findViewById(R.id.addmedicine_medicinepicture);
        submitaddmedicine = findViewById(R.id.btn_addmedicine);
        addmedicine_back = findViewById(R.id.adduserbackbtn_admin);
        progressDialog = new ProgressDialog(AddMedicineData.this);

        medicine = new Medicine();

        addmedicine_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ManageMedicine1.class));
            }
        });

        firestore = FirebaseFirestore.getInstance();
        submitaddmedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String medicine_medicinename = medicinenamemedicine.getText().toString().trim();
                String medicine_serialnumber = serialnumbermedicine.getText().toString().trim();
                String medicine_malnumber = malnumbermedicine.getText().toString().trim();
                String medicine_halalstatus = halalstatusmedicine.getText().toString().trim();
                String medicine_counterfeitstatus = counterfeitstatusmedicine.getText().toString().trim();
                String medicine_manufacturer = manufacturermedicine.getText().toString().trim();
                String medicine_activeing = activeingmedicine.getText().toString().trim();
                String medicine_inactiveing = inactiveingmedicine.getText().toString().trim();
                String medicine_haraming = haramingmedicine.getText().toString().trim();
                String medicine_mushbooging = mushboohingmedicine.getText().toString().trim();
                String medicine_medicinepicture = medicinepicturemedicine.getText().toString().trim();

                if (TextUtils.isEmpty(medicine_medicinename)) {
                    medicinenamemedicine.setError("Medicine name is required!");
                    return;
                }

                if (TextUtils.isEmpty(medicine_serialnumber)) {
                    serialnumbermedicine.setError("Serial Number is required!");
                    return;
                }

                if (TextUtils.isEmpty(medicine_malnumber)) {
                    malnumbermedicine.setError("MAL Number is required!");
                    return;
                }

                if (TextUtils.isEmpty(medicine_halalstatus)) {
                    halalstatusmedicine.setError("Halal Status is required!");
                    return;
                }

                if (TextUtils.isEmpty(medicine_counterfeitstatus)) {
                    counterfeitstatusmedicine.setError("Counterfeit Status is required!");
                    return;
                }

                if (TextUtils.isEmpty(medicine_manufacturer)) {
                    manufacturermedicine.setError("Manufacturer is required!");
                    return;
                }

                if (TextUtils.isEmpty(medicine_activeing)) {
                    activeingmedicine.setError("Active ingredient is required!");
                    return;
                }

                if (TextUtils.isEmpty(medicine_inactiveing)) {
                    inactiveingmedicine.setError("In-Active ingredient is required!");
                    return;
                }

                if (TextUtils.isEmpty(medicine_haraming)) {
                    haramingmedicine.setError("Haram Ingredient is required!");
                    return;
                }

                if (TextUtils.isEmpty(medicine_mushbooging)) {
                    mushboohingmedicine.setError("Mushbooh Ingredient is required!");
                    return;
                }

                if (TextUtils.isEmpty(medicine_medicinepicture)) {
                    medicinepicturemedicine.setError("Medicine Prictue Link is required!");
                    return;
                }

                Map<String, Object> medicine = new HashMap<>();
                medicine.put("medicinename", medicine_medicinename);
                medicine.put("serialnumber", medicine_serialnumber);
                medicine.put("malnumber", medicine_malnumber);
                medicine.put("halalstatus", medicine_halalstatus);
                medicine.put("counterfeitstatus",medicine_counterfeitstatus);
                medicine.put("manufacturer",medicine_manufacturer);
                medicine.put("activeingredient",medicine_activeing);
                medicine.put("inactiveingredient",medicine_inactiveing);
                medicine.put("haramingredient",medicine_haraming);
                medicine.put("mushboohingredient",medicine_mushbooging);
                medicine.put("medicinepicture",medicine_medicinepicture);

                firestore.collection("Medicine").add(medicine).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(AddMedicineData.this, "Medicine Created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), AddMedicineData.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddMedicineData.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
}