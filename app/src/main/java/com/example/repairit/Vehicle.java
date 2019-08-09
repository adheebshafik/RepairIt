package com.example.repairit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Vehicle extends AppCompatActivity {

    TextView vehicle1;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        vehicle1 = findViewById(R.id.viewVehicleTextView);
        add = findViewById(R.id.addNewVehicleBtn);

        vehicle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Vehicle.this, UpdateVehicle.class);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Quotation.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddQuotation(), null).commit();
                Intent intent = new Intent(Vehicle.this, AddVehicle.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
