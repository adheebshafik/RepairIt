package com.example.repairit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UpdateProfile extends AppCompatActivity {

    Button update, delete, back;
    EditText name, address, phone, email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        update = findViewById(R.id.userProfileUpdateBtn);
        delete = findViewById(R.id.userProfileDeleteBtn);
        back = findViewById(R.id.userProfileBackBtn);


        name = findViewById(R.id.eTName);
        address = findViewById(R.id.editText5);
        phone = findViewById(R.id.eTPhone);
        email = findViewById(R.id.editText7);
        email.setEnabled(false);
        pass = findViewById(R.id.eTPass);
        pass.setEnabled(false);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra(Profile.CUST_NAME));
        address.setText(intent.getStringExtra(Profile.CUST_ADDRESS));
        phone.setText(intent.getStringExtra(Profile.CUST_PHONE));
        email.setText(intent.getStringExtra(Profile.CUST_EMAIL));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateProfile();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDeleteDialog();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateProfile.this, Profile.class);
                startActivity(intent);
                finish();
            }
        });

    }



    private void updateProfile() {

        String cName = name.getText().toString();
        String cAddress = address.getText().toString();
        String cPhone = phone.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();

        Intent intent1 = getIntent();
        String cEmail = intent1.getStringExtra(Profile.CUST_EMAIL);

        DatabaseReference profileReference = FirebaseDatabase.getInstance().getReference("customers").child(userId);
        Customer customer = new Customer(userId, cName, cAddress, cPhone, cEmail);
        profileReference.setValue(customer);

        Intent intent = new Intent(UpdateProfile.this, Profile.class);
        Toast.makeText(this, "Profile Updated", Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();

    }

    private void showDeleteDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.delete_dialog, null);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("          Are You Sure ?");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        final Button noBtn = dialogView.findViewById(R.id.confirmDeleteNoBtn);

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateProfile.this, Profile.class));
                finish();
            }
        });

        final Button yesBtn = dialogView.findViewById(R.id.confirmDeleteYesBTn);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProfile();
            }
        });


    }

    private void deleteProfile() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();



        DatabaseReference drCustomers = FirebaseDatabase.getInstance().getReference("customers").child(userId);
        drCustomers.removeValue();

        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(UpdateProfile.this, "Account Deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UpdateProfile.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        Intent intent = new Intent(UpdateProfile.this, LogIn.class);

        startActivity(intent);
        finish();
    }

}







