package com.example.repairit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddAppointment extends AppCompatActivity {

    private Button add, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        back = findViewById(R.id.addAppBackBtn);
        add = findViewById(R.id.addAppAddBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Quotation.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddQuotation(), null).commit();
                Intent intent = new Intent(AddAppointment.this, Appointment.class);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayToastMsg(view);
                Intent intent = new Intent(AddAppointment.this, Appointment.class);
                startActivity(intent);
                finish();
            }
        });



    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(AddAppointment.this, msg, Toast.LENGTH_SHORT);
        toast.show();

    }

    public void displayToastMsg(View v) {

        toastMsg("Appointment Pending");

    }
}
