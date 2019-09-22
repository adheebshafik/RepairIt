package com.example.repairit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddAppointment extends AppCompatActivity {

    EditText vNo, vModel, job, date;
    private Button add, back;
    DatabaseReference databaseAppointment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);


        databaseAppointment = FirebaseDatabase.getInstance().getReference("appointments");


        vNo = findViewById(R.id.addAppointmentVNo);
        vModel = findViewById(R.id.addAppointmentVModel);
        job = findViewById(R.id.addAppJobET);
        date = findViewById(R.id.addAppDateET);
        back = findViewById(R.id.addAppBackBtn);
        add = findViewById(R.id.addAppAddBtn);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AddAppointment.this, Appointment.class);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAppointment();
                Toast.makeText(AddAppointment.this, "Appointment Pending", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddAppointment.this, Appointment.class));
                finish();
            }
        });

    }

    private void addAppointment() {

        String vehicleNo = vNo.getText().toString();
        String vehicleModel = vModel.getText().toString();
        String vehicleJob = job.getText().toString();
        String vehicleAppDate = date.getText().toString();


        if(vehicleNo.isEmpty())
            vNo.setError("Enter Number");
        else if(vehicleModel.isEmpty())
            vModel.setError("Enter Model");
        else if(vehicleJob.isEmpty())

            job.setError("Enter Job");
        else if(vehicleAppDate.isEmpty())
            date.setError("Enter Date");
        else if(!vehicleNo.isEmpty() &&  !vehicleModel.isEmpty()  && !vehicleJob.isEmpty() && !vehicleAppDate.isEmpty() ) {


            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final String userId = user.getUid();

            String appId = databaseAppointment.push().getKey();
            AppointmentObject appointmentObject = new AppointmentObject(appId, vehicleNo, vehicleModel, vehicleJob, vehicleAppDate);
            databaseAppointment.child(userId).child(appId).setValue(appointmentObject);

        }

    }




}
