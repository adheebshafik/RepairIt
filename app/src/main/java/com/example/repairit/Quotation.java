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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Quotation extends AppCompatActivity {


    Button newQuoBtn;
    TextView quo1;
    DatabaseReference databaseQuotations;
    ListView listViewAQuotations;
    List<QuotationObject> listQuotations;

    public static final String QUOTATIONID = "quotationId";
    public static final String VEHICLE_NO = "vehicleno";
    public static final String VEHICLE_MODEL = "vehiclemodel";
    public static final String PHOTO = "damagedescription";
    public static final String APP_JOB = "appointedJob";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();

        databaseQuotations = FirebaseDatabase.getInstance().getReference("quotations").child(userId);
        listQuotations = new ArrayList<>();
        listViewAQuotations = findViewById(R.id.listViewQuotations);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

//        quo1 = findViewById(R.id.viewQuo1TextView);
//        quo1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(Quotation.this, UpdateQuotation.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        newQuoBtn = findViewById(R.id.newQuoBtn);
        newQuoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Quotation.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddQuotation(), null).commit();
                Intent intent = new Intent(Quotation.this, AddQuotation.class);
                startActivity(intent);
                finish();
            }
        });


        listViewAQuotations.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                QuotationObject quotationObject = listQuotations.get(i);

                showUpdateDialog(quotationObject.getQuotationId(), quotationObject.getVehicleNo());

                return false;
            }
        });


        listViewAQuotations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                QuotationObject quotationObject = listQuotations.get(i);

                Intent intent = new Intent(getApplicationContext(), UpdateQuotation.class);

                intent.putExtra(QUOTATIONID, quotationObject.getQuotationId());
                intent.putExtra(VEHICLE_NO, quotationObject.getVehicleNo());
                intent.putExtra(VEHICLE_MODEL, quotationObject.getVehicleModel());
                intent.putExtra(PHOTO, quotationObject.getImgurl());
                intent.putExtra(APP_JOB, quotationObject.getJobAppointed());

                startActivity(intent);

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseQuotations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Clearing the previous artist list
                listQuotations.clear();

                //iterating through all the nodes
                for(DataSnapshot artistSnapShot : dataSnapshot.getChildren()) {
                    //getting artist
                    QuotationObject artist = artistSnapShot.getValue(QuotationObject.class);
                    //adding artist to the list
                    listQuotations.add(artist);
                }

                QuotationList adapter = new QuotationList(Quotation.this, listQuotations);
                listViewAQuotations.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showUpdateDialog(final String quotationID, String vehicleNo) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Delete Vehicle Number " + vehicleNo + "?");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        final Button deleteButton = dialogView.findViewById(R.id.updateDialogDeleteBtn);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteQuotation(quotationID);
            }
        });



    }

    private void deleteQuotation(String quotationId) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();

        DatabaseReference drQuotation = FirebaseDatabase.getInstance().getReference("quotations").child(userId).child(quotationId);
        drQuotation.removeValue();
        startActivity(new Intent(this, Quotation.class));
        finish();

    }


}

