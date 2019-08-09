package com.example.repairit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UpdateAppointment extends AppCompatActivity {

    Button update, delete, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_appointment);

        update = findViewById(R.id.updateAppUpdateBtn);
        delete = findViewById(R.id.updateAppDeleteBtn);
        back = findViewById(R.id.updateAppBackBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UpdateAppointment.this, Appointment.class);
                startActivity(intent);
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayToastMsg(view);
                Intent intent = new Intent(UpdateAppointment.this, Appointment.class);
                startActivity(intent);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDeleteToastMsg(view);
                Intent intent = new Intent(UpdateAppointment.this, Appointment.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(UpdateAppointment.this, msg, Toast.LENGTH_SHORT);
        toast.show();

    }

    public void displayToastMsg(View v) {

        toastMsg("Update Successful");

    }

    public void displayDeleteToastMsg(View v) {

        toastMsg("Successfully Deleted");

    }

}


