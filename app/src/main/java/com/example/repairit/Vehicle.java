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

public class Vehicle extends AppCompatActivity {


    Button add;
    DatabaseReference databaseVehicles;
    ListView listViewVehicles;
    List<VehicleObject> listVehicle;

    public static final String VEHICLE_NO = "vehicleno";
    public static final String VEHICLE_MODEL = "vehiclemodel";
    public static final String VEHICLE_TYPE = "vehicleType";
    public static final String VEHICLE_COLOR = "vehicleColor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();

        databaseVehicles = FirebaseDatabase.getInstance().getReference("vehicles").child(userId);
        listVehicle = new ArrayList<>();
        listViewVehicles = findViewById(R.id.lisViewVehicles);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        add = findViewById(R.id.addNewVehicleBtn);

//        vehicle1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(Vehicle.this, UpdateVehicle.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Quotation.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddQuotation(), null).commit();
                Intent intent = new Intent(Vehicle.this, AddVehicle.class);
                startActivity(intent);
                finish();
            }
        });

        listViewVehicles.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                VehicleObject vehicleObject = listVehicle.get(i);

                showUpdateDialog(vehicleObject.getVehicleNo());

                return false;
            }
        });

        listViewVehicles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VehicleObject vehicleObject = listVehicle.get(i);

                Intent intent = new Intent(getApplicationContext(), UpdateVehicle.class);

                intent.putExtra(VEHICLE_NO, vehicleObject.getVehicleNo());
                intent.putExtra(VEHICLE_MODEL, vehicleObject.getVehicleModel());
                intent.putExtra(VEHICLE_TYPE, vehicleObject.getVehicleType());
                intent.putExtra(VEHICLE_COLOR, vehicleObject.getVehicleColor());

                startActivity(intent);

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseVehicles.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Clearing the previous artist list
                listVehicle.clear();

                //iterating through all the nodes
                for(DataSnapshot artistSnapShot : dataSnapshot.getChildren()) {
                    //getting artist
                    VehicleObject artist = artistSnapShot.getValue(VehicleObject.class);
                    //adding artist to the list
                    listVehicle.add(artist);
                }

                VehicleList adapter = new VehicleList(Vehicle.this, listVehicle);
                listViewVehicles.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showUpdateDialog(final String vehicleNo) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_vehicle_dialog, null);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Delete Vehicle Number " + vehicleNo + "?");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        final Button deleteButton = dialogView.findViewById(R.id.updateVehicleDialogDeleteBtn);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteVehicle(vehicleNo);
            }
        });

    }

    private void deleteVehicle(String vehicleNo) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();

        DatabaseReference drVehicle = FirebaseDatabase.getInstance().getReference("vehicles").child(userId).child(vehicleNo);
        drVehicle.removeValue();
        startActivity(new Intent(this, Vehicle.class));
        finish();

    }
}
