package com.example.repairit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Appointment extends AppCompatActivity {



    Button addAppBtn;
    DatabaseReference databaseAppointment;
    ListView listViewAppointment;
    List<AppointmentObject> listAppointment;


    public static final String APPOINTMENT_ID = "appoinmentId";
    public static final String VEHICLE_NO = "vehicleno";
    public static final String VEHICLE_MODEL = "vehiclemodel";
    public static final String JOB = "appJob";
    public static final String DATE = "appointedDate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();

        databaseAppointment = FirebaseDatabase.getInstance().getReference("appointments").child(userId);
        listAppointment = new ArrayList<>();
        listViewAppointment = findViewById(R.id.listViewAppointments);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        addAppBtn = findViewById(R.id.addAppointmentBtn);


        addAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Quotation.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddQuotation(), null).commit();
                Intent intent = new Intent(Appointment.this, AddAppointment.class);
                startActivity(intent);
                finish();
            }
        });

//        viewApp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(Appointment.this, UpdateAppointment.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        listViewAppointment.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                AppointmentObject appointmentObject = listAppointment.get(i);

                showUpdateDialog(appointmentObject.getAppointmentId(), appointmentObject.getVehicleNo());

                return false;
            }
        });

        listViewAppointment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AppointmentObject appointmentObject = listAppointment.get(i);

                Intent intent = new Intent(getApplicationContext(), UpdateAppointment.class);

                intent.putExtra(APPOINTMENT_ID, appointmentObject.getAppointmentId());
                intent.putExtra(VEHICLE_NO, appointmentObject.getVehicleNo());
                intent.putExtra(VEHICLE_MODEL, appointmentObject.getVehicleModel());
                intent.putExtra(JOB, appointmentObject.getAppointedJob());
                intent.putExtra(DATE, appointmentObject.getDate());

                startActivity(intent);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseAppointment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Clearing the previous artist list
                listAppointment.clear();

                //iterating through all the nodes
                for(DataSnapshot artistSnapShot : dataSnapshot.getChildren()) {
                    //getting artist
                    AppointmentObject artist = artistSnapShot.getValue(AppointmentObject.class);
                    //adding artist to the list
                    listAppointment.add(artist);
                }

                AppointmentList adapter = new AppointmentList(Appointment.this, listAppointment);
                listViewAppointment.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showUpdateDialog(final String appointmentId, String vehicleNo) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_appointment_dialog, null);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Delete Appointment with Vehicle Number " + vehicleNo + "?");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        final Button deleteButton = dialogView.findViewById(R.id.updateAppointmentDeleteBtn);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAppointment(appointmentId);
            }
        });

    }

    private void deleteAppointment(String appointmentID) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();

        DatabaseReference drAppointment = FirebaseDatabase.getInstance().getReference("appointments").child(userId).child(appointmentID);
        drAppointment.removeValue();
        startActivity(new Intent(this, Appointment.class));
        finish();

    }
}
