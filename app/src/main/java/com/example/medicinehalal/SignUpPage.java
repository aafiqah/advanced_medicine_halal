package com.example.medicinehalal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpPage extends AppCompatActivity {

    ImageView Ssignupback;
    EditText Sfullnametxt, Semailtxt, Spwdtxt, Sphonetxt, Sroletxt;
    CheckBox Scheckbox;
    Button Ssignup;
    FirebaseAuth Sfauth;
    FirebaseFirestore Sfstore;
    String SuserID;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");

    public static final String TAG = "SignUpPage";

    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[+]?[0-9]{10,13}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Ssignupback = findViewById(R.id.registerbackbtn_register);
        Sfullnametxt = findViewById(R.id.register_fullname);
        Semailtxt = findViewById(R.id.register_email);
        Spwdtxt = findViewById(R.id.registerpass);
        Sphonetxt = findViewById(R.id.registerphone);
        Scheckbox = findViewById(R.id.registercheckBox);
        Ssignup = findViewById(R.id.btn_register);
        Sroletxt = findViewById(R.id.registerrole);
        Sfauth = FirebaseAuth.getInstance();
        Sfstore = FirebaseFirestore.getInstance();

        Ssignupback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginRegisterOption.class));
            }
        });

        Scheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    Spwdtxt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    Spwdtxt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        Ssignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = Semailtxt.getText().toString().trim();
                String password = Spwdtxt.getText().toString().trim();
                String fullname = Sfullnametxt.getText().toString();
                String phone = Sphonetxt.getText().toString();
                String role = Sroletxt.getText().toString();

                if(TextUtils.isEmpty(fullname))
                {
                    Sfullnametxt.setError("Full name is required!");
                    return;
                }

                if(TextUtils.isEmpty(email))
                {
                    Semailtxt.setError("Email is required!");
                    return;
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Semailtxt.setError("Please enter a valid email address");
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    Spwdtxt.setError("Password is required!");
                    return;
                }
                else if(!PASSWORD_PATTERN.matcher(password).matches())
                {
                    Spwdtxt.setError("Password to weak and must more than 6 characters!");
                    return;
                }

                if(TextUtils.isEmpty(phone))
                {
                    Sphonetxt.setError("Phone number is required!");
                    return;
                }
                else if(!MOBILE_PATTERN.matcher(phone).matches())
                {
                    Sphonetxt.setError("Please enter a valid mobile number");
                    return;
                }

                Sfauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignUpPage.this, "User Created", Toast.LENGTH_SHORT).show();

                            //store data
                            SuserID = Sfauth.getCurrentUser().getUid();
                            DocumentReference documentReference = Sfstore.collection("Users").document(SuserID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("fullname", fullname);
                            user.put("email", email);
                            user.put("password", password);
                            user.put("phone", phone);
                            user.put("role",role);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    Log.d(TAG, "onSuccess: user profile is created for" + SuserID);
                                }
                            });

                            startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                        }
                        else
                        {
                            Toast.makeText(SignUpPage.this, "Error!" + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}