package com.example.medicinehalal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class LoginPage extends AppCompatActivity {

    ImageView Lloginback;
    EditText Lemailtxt, Lpwdtxt;
    CheckBox Lcheckbox;
    Button Lforgetpwd, Llogin;
    FirebaseAuth Lfauth;
    FirebaseFirestore Lfstore;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Lemailtxt = findViewById(R.id.emailtxt_login);
        Lpwdtxt = findViewById(R.id.pwdtxt_login);
        Lloginback = findViewById(R.id.loginbackbtn_login);
        Lcheckbox = findViewById(R.id.showpwdcheck_login);
        Lforgetpwd = findViewById(R.id.forgetpwdbtn_login);
        Llogin = findViewById(R.id.btn_login);
        Lfauth = FirebaseAuth.getInstance();
        Lfstore = FirebaseFirestore.getInstance();

        Lloginback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginRegisterOption.class));
            }
        });

        Lcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    Lpwdtxt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    Lpwdtxt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        Llogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String login_email = Lemailtxt.getText().toString().trim();
                String login_password = Lpwdtxt.getText().toString().trim();

                if(TextUtils.isEmpty(login_email))
                {
                    Lemailtxt.setError("Email is required!");
                    return;
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(login_email).matches())
                {
                    Lemailtxt.setError("Please enter a valid email address");
                    return;
                }

                if(TextUtils.isEmpty(login_password))
                {
                    Lpwdtxt.setError("Password is required!");
                    return;
                }
                else if(login_password.length() < 6)
                {
                    Lpwdtxt.setError("Password must be more than 6 characters");
                    return;
                }
                else if(!PASSWORD_PATTERN.matcher(login_password).matches())
                {
                    Lpwdtxt.setError("Password to weak and must more than 6 characters!");
                    return;
                }

                Lfauth.signInWithEmailAndPassword(login_email,login_password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(LoginPage.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            checkUserlevel(Lfauth.getUid());
                        }
                        else
                        {
                            Toast.makeText(LoginPage.this, "Error!" + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        Lforgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetpassword = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link: ");
                passwordResetDialog.setView(resetpassword);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        String mail = resetpassword.getText().toString();

                        Lfauth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(LoginPage.this, "Reset Link sent to Your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(LoginPage.this, "Error! Reset link is not sent", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                    }
                });
                passwordResetDialog.create().show();
            }
        });
    }

    private void checkUserlevel(String uid) {
        DocumentReference Ldf = Lfstore.collection("Users").document(uid);

        //extract the data from document
        Ldf.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot snapshot)
            {
                Log.d("TAG","onSuccess" + snapshot.getData());

                //identify the user access level
                if(snapshot.getString("role").equals("Admin"))
                {
                    startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                    finish();
                }
                if(snapshot.getString("role").equals("User"))
                {
                    startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                    finish();
                }
            }
        });
    }
}