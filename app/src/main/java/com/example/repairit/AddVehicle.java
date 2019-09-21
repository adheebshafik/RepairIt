package com.example.repairit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddVehicle extends AppCompatActivity {

    Button add,back;
    EditText vNo, vModel, vType, vColor;
    DatabaseReference databaseVehicle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseVehicle = FirebaseDatabase.getInstance().getReference("vehicles").child(uid);

        vNo = findViewById(R.id.addVehicleNoEditText);
        vModel = findViewById(R.id.addVehicleModelEditText);
        vType = findViewById(R.id.addVehicleTypeEditText);
        vColor = findViewById(R.id.addVehicleColorEditText);

        add = findViewById(R.id.addVehicleaddBtn);
        back= findViewById(R.id.addVehicleBackBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVehicle();
                Toast.makeText(AddVehicle.this, "Vehicle Added Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddVehicle.this, Vehicle.class));
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

    private void addVehicle() {

        String vehicleNo = vNo.getText().toString();
        String vehicleModel = vModel.getText().toString();
        String vehicleType = vType.getText().toString();
        String vehicleColor = vColor.getText().toString();

        if(vehicleNo.isEmpty()) {
            vNo.setError("Enter Vehicle No");
        } else if(vehicleModel.isEmpty())
            vModel.setError("Enter Model");
        else if(vehicleType.isEmpty())
            vType.setError("Enter Type");
        else if(vehicleColor.isEmpty())
            vColor.setError("Enter Color");
        else if(!vehicleNo.isEmpty() && !vehicleModel.isEmpty() && !vehicleType.isEmpty() && !vehicleColor.isEmpty()) {

            VehicleObject vehicleObject = new VehicleObject(vehicleNo, vehicleModel, vehicleType, vehicleColor);
            databaseVehicle.child(vehicleNo).setValue(vehicleObject);

        }

    }
}
