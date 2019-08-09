package com.example.repairit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        submit = findViewById(R.id.contactUsSubmitBtn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayToastMsg(view);
                Intent intent = new Intent(ContactUs.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(ContactUs.this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void displayToastMsg(View v) {
        toastMsg("We will get back to you shortly");
    }

}
