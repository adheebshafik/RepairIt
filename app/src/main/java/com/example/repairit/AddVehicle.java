package com.example.repairit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddVehicle extends AppCompatActivity {

    Button add,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        add = findViewById(R.id.addVehicleaddBtn);
        back= findViewById(R.id.addVehicleBackBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayToastMsg(view);
                Intent intent = new Intent(AddVehicle.this, Vehicle.class);
                startActivity(intent);
                finish();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddVehicle.this, Vehicle.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(AddVehicle.this, msg, Toast.LENGTH_SHORT);
        toast.show();

    }

    public void displayToastMsg(View v) {

        toastMsg("Vehicle Added Successfully");

    }
}
