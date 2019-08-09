package com.example.repairit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class AddQuotation extends AppCompatActivity {

    private Button add, back;
    private final String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quotation);

        Spinner spinner = (Spinner) findViewById(R.id.vModelSpinner);
        ArrayAdapter<String> myAdapter =  new ArrayAdapter<String>(AddQuotation.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.vehicleList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        back = findViewById(R.id.newQuoBackButton);
        add = findViewById(R.id.newQuoAddBtn);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Quotation.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddQuotation(), null).commit();
                Intent intent = new Intent(AddQuotation.this, Quotation.class);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayToastMsg(view);
                displayNotification(view);
                Intent intent = new Intent(AddQuotation.this, Quotation.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(AddQuotation.this, msg, Toast.LENGTH_SHORT);
        toast.show();

    }

    public void displayToastMsg(View v) {

        toastMsg("Quotation Added Successfully");

    }

    public void displayNotification(View view) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(AddQuotation.this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_sms);
        builder.setContentTitle("Repairit ALERT");
        builder.setContentText("Quotation will sent by SMS within 24 hours after evaluation");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(AddQuotation.this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

    }
}
