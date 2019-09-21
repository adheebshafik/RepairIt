package com.example.repairit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {

    Button update;
    DatabaseReference databaseProfile;
//    List<Customer> customerList;
//    FirebaseAuth mfirebaseAuth;

    public static final String CUST_NAME = "custName";
    public static final String CUST_ADDRESS = "custAddress";
    public static final String CUST_PHONE = "custPhone";
    public static final String CUST_EMAIL = "custEmail";

    TextView name, address, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        databaseProfile = FirebaseDatabase.getInstance().getReference("customers");

        update = findViewById(R.id.profileUpdateBtn);



        loadUserInformation();

        name = findViewById(R.id.textView7);
        address = findViewById(R.id.textView9);
        phone = findViewById(R.id.textView11);
        email = findViewById(R.id.textView13);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Profile.this, UpdateProfile.class);

                intent.putExtra(CUST_NAME, name.getText());
                intent.putExtra(CUST_ADDRESS, address.getText());
                intent.putExtra(CUST_PHONE, phone.getText());
                intent.putExtra(CUST_EMAIL, email.getText());
                startActivity(intent);
                finish();
            }
        });

    }

    private void loadUserInformation() {

        name = findViewById(R.id.textView7);
        address = findViewById(R.id.textView9);
        phone = findViewById(R.id.textView11);
        email = findViewById(R.id.textView13);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();


        databaseProfile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Customer customer = dataSnapshot.child(userId).getValue(Customer.class);
                name.setText(customer.getFirstName());
                address.setText(customer.getAddress());
                phone.setText(customer.getMobile());
                email.setText(customer.getEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
