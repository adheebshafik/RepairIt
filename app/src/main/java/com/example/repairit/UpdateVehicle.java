package com.example.repairit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateVehicle extends AppCompatActivity {

    Button update, back;
    EditText vNo, vModel, vType, vColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vehicle);

        update = findViewById(R.id.updateVehicleUpdateBtn);
        back = findViewById(R.id.updateVehicleBackBtn);

        vNo = findViewById(R.id.editTextVN);
        vModel = findViewById(R.id.editTextVM);
        vType = findViewById(R.id.editTextVT);
        vColor = findViewById(R.id.editTextVC);

        Intent intent = getIntent();
        final String vcehNo = intent.getStringExtra(Vehicle.VEHICLE_NO);
        String vcehModel = intent.getStringExtra(Vehicle.VEHICLE_MODEL);
        String vchType = intent.getStringExtra(Vehicle.VEHICLE_TYPE);
        String vchColor = intent.getStringExtra(Vehicle.VEHICLE_COLOR);


        vNo.setText(vcehNo);
        vModel.setText(vcehModel);
        vType.setText(vchType);
        vColor.setText(vchColor);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateVehicle.this, Vehicle.class);
                startActivity(intent);
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateVehicle(vcehNo);

            }
        });

    }

    private void updateVehicle(final String vchId) {


        String a = vNo.getText().toString();
        String b = vModel.getText().toString();
        String c = vType.getText().toString();
        String d = vColor.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();

        if (vchId  == a ) {
            DatabaseReference vReference = FirebaseDatabase.getInstance().getReference("vehicles").child(userId).child(vchId);
            VehicleObject vehicleObject = new VehicleObject(a, b, c, d);
            vReference.setValue(vehicleObject);
        } else {
            DatabaseReference drVehicle = FirebaseDatabase.getInstance().getReference("vehicles").child(userId).child(vchId);
            drVehicle.removeValue();
            DatabaseReference v2Reference = FirebaseDatabase.getInstance().getReference("vehicles").child(userId).child(a);
            VehicleObject vehicleObject = new VehicleObject(a, b, c, d);
            v2Reference.setValue(vehicleObject);
        }



        Toast.makeText(UpdateVehicle.this, "Update Successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(UpdateVehicle.this, Vehicle.class));
        finish();

    }
}
