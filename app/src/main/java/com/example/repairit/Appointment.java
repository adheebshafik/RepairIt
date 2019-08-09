package com.example.repairit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Appointment extends AppCompatActivity {

    Button addAppBtn;
    TextView viewApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewApp = findViewById(R.id.viewAppText);
        addAppBtn = findViewById(R.id.addAppointmentBtn);

        viewApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Appointment.this, UpdateAppointment.class);
                startActivity(intent);
                finish();
            }
        });

        addAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Quotation.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddQuotation(), null).commit();
                Intent intent = new Intent(Appointment.this, AddAppointment.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
