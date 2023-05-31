package com.example.medicinehalal;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ReportForm1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;

    EditText Rfullname, Remail, Rphone, Rmedicinename, Rlocation, Radditional;
    ImageView Rphotogallery;
    Button Ropencamera, Ruploadpic, Rsubmit;
    RadioGroup Rradiogroup;
    RadioButton Rmedicineissue;
    ProgressDialog progressDialog;
    FirebaseFirestore Rfstore;
    FirebaseAuth Rfauth;
    StorageReference storageReference;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar4;
    DatabaseReference databaseReference;
    Report report;
    String currentPhotoPath;
    Uri contentUri;

    public static final String TAG = "ReportForm1";
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[+]?[0-9]{10,13}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_form1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Rfullname = findViewById(R.id.report_fullname);
        Remail = findViewById(R.id.report_email);
        Rphone = findViewById(R.id.report_phone);
        Rmedicinename = findViewById(R.id.report_medicinename);
        Rlocation = findViewById(R.id.report_medicinelocation);
        Radditional = findViewById(R.id.report_medicineadditional);
        Ropencamera = findViewById(R.id.report_camerabtn);
        Ruploadpic = findViewById(R.id.report_uploadbtn);
        Rradiogroup = findViewById(R.id.report_radiogroup);
        Rsubmit = findViewById(R.id.report_submitbtn);
        Rphotogallery = findViewById(R.id.display_picture);
        Rfstore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(ReportForm1.this);

        drawerLayout = findViewById(R.id.reportform1_drawer);
        navigationView = findViewById(R.id.nav_view);
        toolbar4 = findViewById(R.id.sidemenu4);

        //toolbar
        setSupportActionBar(toolbar4);

        storageReference = FirebaseStorage.getInstance().getReference("Report");
        databaseReference= FirebaseDatabase.getInstance().getReference("Report");

        //navigation drawer
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar4, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.navreportform);

        report = new Report();

        Ropencamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions();
            }
        });

        Ruploadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });


        Rsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    SubmitReport();
            }
        });
    }

    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            dispatchTakePictureIntent();
        }else{
            ActivityCompat.requestPermissions(ReportForm1.this, new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERM_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera permission is required to use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                File f = new File(currentPhotoPath);
                Rphotogallery.setImageURI(Uri.fromFile(f));
                Log.d("tag", "Absolute Url of Image is " + Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);
                Log.d("tag", "onActivityResult: Gallery Image Uri:  " + imageFileName);
                Rphotogallery.setImageURI(contentUri);
            }
        }
    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg" ,        /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.medicinehalal.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePictureIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private boolean validateMedicineIssue(){
        if (Rradiogroup.getCheckedRadioButtonId()==-1){
            Toast.makeText(ReportForm1.this,"Please Select Medicine Issue", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    public  void SubmitReport(){
        String report_fullname = Rfullname.getText().toString().trim();
        String report_email = Remail.getText().toString().trim();
        String report_phone = Rphone.getText().toString();
        String report_medicinename = Rmedicinename.getText().toString();
        String report_medicinelocation = Rlocation.getText().toString();
        String report_additional = Radditional.getText().toString();
        Rmedicineissue=findViewById(Rradiogroup.getCheckedRadioButtonId());

        if (TextUtils.isEmpty(report_fullname)) {
            Rfullname.setError("Full name is required!");
            return;
        }

        if (TextUtils.isEmpty(report_email)) {
            Remail.setError("Email is required!");
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(report_email).matches()) {
            Remail.setError("Please enter a valid email address");
            return;
        }

        if (TextUtils.isEmpty(report_phone)) {
            Rphone.setError("Phone number is required!");
            return;
        } else if (!MOBILE_PATTERN.matcher(report_phone).matches()) {
            Rphone.setError("Please enter a valid mobile number");
            return;
        }

        if (TextUtils.isEmpty(report_medicinename)) {
            Rmedicinename.setError("Full name is required!");
            return;
        }

        if (TextUtils.isEmpty(report_medicinelocation)) {
            Rlocation.setError("Full name is required!");
            return;
        }

        if(!validateMedicineIssue()){
            return;
        }

        if (TextUtils.isEmpty(report_additional)) {
            Radditional.setError("Required!");
            return;
        }

        if(contentUri != null)
        {
            progressDialog.setTitle("Report is Uploading...");
            progressDialog.show();
            StorageReference storageReference2= storageReference.child(System.currentTimeMillis()+"."+getFileExt(contentUri));

            storageReference2.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String report_fullname = Rfullname.getText().toString().trim();
                    String report_email = Remail.getText().toString().trim();
                    String report_phone = Rphone.getText().toString();
                    String report_medicinename = Rmedicinename.getText().toString();
                    String report_medicinelocation = Rlocation.getText().toString();
                    Rmedicineissue=findViewById(Rradiogroup.getCheckedRadioButtonId());
                    String report_medicineissue= Rmedicineissue.getText().toString();
                    String report_additional= Radditional.getText().toString();

                    @SuppressWarnings("VisibleForTests")
                    Report reportinfo = new Report(report_fullname, report_email, report_phone, report_medicinename, report_medicinelocation, taskSnapshot.getUploadSessionUri().toString(), report_medicineissue, report_additional);
                    String ImageUploadId = databaseReference.push().getKey();
                    databaseReference.child(ImageUploadId).setValue(reportinfo);

                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Report Submitted", Toast.LENGTH_LONG).show();
                    Intent donesubmit = new Intent(ReportForm1.this, UserDashboard.class);
                    startActivity(donesubmit);
                }
            });
        }
        else{
            Toast.makeText(ReportForm1.this, "Please fill all fields", Toast.LENGTH_LONG).show();
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
                Intent home = new Intent(ReportForm1.this, UserDashboard.class);
                startActivity(home);
                break;
            case R.id.navhalaldetection:
                Intent halal = new Intent(ReportForm1.this, HalalDetection1.class);
                startActivity(halal);
                break;
            case R.id.navcounterfeitdetection:
                Intent counterfeit = new Intent(ReportForm1.this, CounterfeitDetection1.class);
                startActivity(counterfeit);
                break;
            case R.id.navreportform:
                Intent report = new Intent(ReportForm1.this, ReportForm1.class);
                startActivity(report);
                break;
            case R.id.navlogout:
                Rfauth.signOut();
                finish();
                Intent logout = new Intent(ReportForm1.this, LoginRegisterOption.class);
                startActivity(logout);
                finish();
                Toast.makeText(ReportForm1.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}