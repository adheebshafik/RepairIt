package com.example.repairit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UpdateVehicle extends AppCompatActivity {

    Button update, delete, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vehicle);

        update = findViewById(R.id.updateVehicleUpdateBtn);
        delete = findViewById(R.id.updateVehicleDeleteTbtn);
        back = findViewById(R.id.updateVehicleBackBtn);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayToastMsg(view);
                Intent intent = new Intent(UpdateVehicle.this, Vehicle.class);
                startActivity(intent);
                finish();
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDeleteMsg(view);
                Intent intent = new Intent(UpdateVehicle.this, Vehicle.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateVehicle.this, Vehicle.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(UpdateVehicle.this, msg, Toast.LENGTH_SHORT);
        toast.show();

    }


    public void displayToastMsg(View v) {

        toastMsg("Vehicle Updated Successfully");

    }

    public void displayDeleteMsg(View v) {

        toastMsg("Vehicle Deleted Successfully");

    }
}
