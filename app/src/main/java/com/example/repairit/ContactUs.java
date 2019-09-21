package com.example.repairit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactUs extends AppCompatActivity {

    Button submit;
    DatabaseReference databaseMessages;
    EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseMessages = FirebaseDatabase.getInstance().getReference("contactUsMessages").child(uid);

        submit = findViewById(R.id.contactUsSubmitBtn);
        message = findViewById(R.id.contactUsMessage);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitMessage();
            }
        });

    }


    private void submitMessage() {

        String msg = message.getText().toString();
        String uid2 = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if(msg.isEmpty()) {
            message.setError("Enter a Message");
        } else {

            MessageObject messageObject = new MessageObject(uid2, msg);
            databaseMessages.child(uid2).setValue(messageObject);
            Toast.makeText(ContactUs.this, "We will get back to you shortly", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ContactUs.this, Home.class);
            startActivity(intent);
            finish();

        }

    }

}
