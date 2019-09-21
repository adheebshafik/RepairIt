package com.example.repairit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    public static final String CUSTOMER_ID = "customerid";

    EditText name, address, email, phone, password;
    Button register;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseCustomers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseCustomers = FirebaseDatabase.getInstance().getReference("customers");

        name = findViewById(R.id.et_name);
        address = findViewById(R.id.et_address);
        phone = findViewById(R.id.et_phone);
        email = findViewById(R.id.et_reg_email);
        password = findViewById(R.id.et_reg_password);
        register = findViewById(R.id.btn_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register();
            }
        });


    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(Registration.this, msg, Toast.LENGTH_SHORT);
        toast.show();

    }

    public void displayToastMsg(View v) {

        toastMsg("Registration Successful");

    }

    private void register() {

        String userName = name.getText().toString();
        String useraddress = address.getText().toString();
        String userphone = phone.getText().toString();
        String useremail = email.getText().toString();
        String userpassword = password.getText().toString();

        if(userName.isEmpty()) {
            name.setError("Enter Name");
        } else if(useraddress.isEmpty())
            address.setError("Enter Address");
        else if(userphone.isEmpty())
            phone.setError("Enter phone");
        else if(useremail.isEmpty())
            email.setError("Enter email");
        else if(userpassword.isEmpty())
            password.setError("Enter password");
        else if(!userName.isEmpty() && !useraddress.isEmpty() && !userphone.isEmpty() && !useremail.isEmpty() && !userpassword.isEmpty()) {

            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        addCustomer();
                        Toast.makeText(Registration.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                        startActivity( new Intent(Registration.this, LogIn.class));
                        finish();

                    } else {
                        Toast.makeText(Registration.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            });

        }

    }

    private void addCustomer() {

        String userName = name.getText().toString();
        String useraddress = address.getText().toString();
        String userphone = phone.getText().toString();
        String useremail = email.getText().toString();
        String userpassword = password.getText().toString();

        if(userName.isEmpty()) {
            name.setError("Enter Name");
        } else if(useraddress.isEmpty())
            address.setError("Enter Address");
        else if(userphone.isEmpty())
            phone.setError("Enter phone");
        else if(useremail.isEmpty())
            email.setError("Enter email");
        else if(userpassword.isEmpty())
            password.setError("Enter password");
        else if(!userName.isEmpty() && !useraddress.isEmpty() && !userphone.isEmpty() && !useremail.isEmpty() && !userpassword.isEmpty()) {



            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            String custId = databaseCustomers.push().getKey();
            Customer customer = new Customer(uid, userName, useraddress, userphone, useremail, userpassword);
            databaseCustomers.child(uid).setValue(customer);


        }


    }

}
