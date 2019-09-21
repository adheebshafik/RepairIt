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

public class UpdateAppointment extends AppCompatActivity {

    Button update, back;
    EditText vNo, vModel, vJob, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_appointment);

        update = findViewById(R.id.updateAppUpdateBtn);
        back = findViewById(R.id.updateAppBackBtn);

        vNo = findViewById(R.id.updateAppVehicleNo);
        vModel = findViewById(R.id.updateAppVehicleText);
        vJob = findViewById(R.id.updateAppJobText);
        date = findViewById(R.id.updateAppDateText);

        Intent intent = getIntent();
        final String appId = intent.getStringExtra(Appointment.APPOINTMENT_ID);
        String vcehNo = intent.getStringExtra(Appointment.VEHICLE_NO);
        String vcehModel = intent.getStringExtra(Appointment.VEHICLE_MODEL);
        String vchJob = intent.getStringExtra(Appointment.JOB);
        String vchDate = intent.getStringExtra(Appointment.DATE);

        vNo.setText(vcehNo);
        vModel.setText(vcehModel);
        vJob.setText(vchJob);
        date.setText(vchDate);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(UpdateAppointment.this, Appointment.class));
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                updateAppointment(appId);


            }
        });

    }

    private void updateAppointment(final String appId) {


        String a = vNo.getText().toString();
        String b = vModel.getText().toString();
        String c = vJob.getText().toString();
        String d = date.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();

        DatabaseReference appReference = FirebaseDatabase.getInstance().getReference("appointments").child(userId).child(appId);
        AppointmentObject appointmentObject = new AppointmentObject(appId, a, b, c, d);
        appReference.setValue(appointmentObject);
        Toast.makeText(UpdateAppointment.this, "Update Successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(UpdateAppointment.this, Appointment.class));
        finish();

    }

}


