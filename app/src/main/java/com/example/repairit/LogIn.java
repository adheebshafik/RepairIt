package com.example.repairit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageTask;

public class LogIn extends AppCompatActivity {

    TextView register;
    EditText email, password;
    Button login;
    FirebaseAuth mfirebaseAuth;
    private ProgressDialog progressDialog;
    private StorageTask uploadTask;

//    private FirebaseAuth.AuthStateListener mAuthStateAListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        progressDialog = new ProgressDialog(this);
        mfirebaseAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.etEmailLogin);
        password = findViewById(R.id.etPasswordLogin);
        login = findViewById(R.id.loginBtn);
        register = findViewById(R.id.registerLoginText);

//
//        mAuthStateAListener = new FirebaseAuth.AuthStateListener() {
//
//            FirebaseUser mFirebaseUser = mfirebaseAuth.getCurrentUser();
//
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth userId) {
//                if ( mFirebaseUser != null) {
//                    Toast.makeText(LogIn.this, "You are logged in", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(LogIn.this, Splash.class));
//                } else {
//                    Toast.makeText(LogIn.this, "Please Login", Toast.LENGTH_SHORT).show();
//                }
//            }
//        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String useremail = email.getText().toString().trim();
                String userPass = password.getText().toString().trim();

                if(useremail.isEmpty())
                    email.setError("Enter Email");
                else if (userPass.isEmpty())
                    password.setError("Enter Password");
                else if(!useremail.isEmpty() && !userPass.isEmpty()) {

                    progressDialog.setMessage("Logging in");
                    progressDialog.show();



                    if(uploadTask != null && uploadTask.isInProgress())
                        Toast.makeText(LogIn.this, "Logging in", Toast.LENGTH_LONG).show();

                    mfirebaseAuth.signInWithEmailAndPassword(useremail, userPass)


                            .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {


                                        startActivity(new Intent(LogIn.this, Splash.class));
                                        finish();
                                    } else {
                                        Toast.makeText(LogIn.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                    }

                                }
                            });


                }

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, Registration.class);
                startActivity(intent);
            }
        });



    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mfirebaseAuth.addAuthStateListener(mAuthStateAListener);
//    }

}
