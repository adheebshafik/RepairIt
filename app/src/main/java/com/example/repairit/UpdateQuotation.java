package com.example.repairit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateQuotation extends AppCompatActivity {

    Button update, delete, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_quotation);


        Spinner spinner = (Spinner) findViewById(R.id.vModelSpinner2);
        ArrayAdapter<String> myAdapter =  new ArrayAdapter<String>(UpdateQuotation.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.vehicleList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        update = findViewById(R.id.quoDetailsUpdateBtn);
        delete = findViewById(R.id.quoDetailsDeleteBtn);
        back = findViewById(R.id.quoDetailsBackBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UpdateQuotation.this, Quotation.class);
                startActivity(intent);
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayToastMsg(view);
                Intent intent = new Intent(UpdateQuotation.this, Quotation.class);
                startActivity(intent);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDeleteToastMsg(view);
                Intent intent = new Intent(UpdateQuotation.this, Quotation.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(UpdateQuotation.this, msg, Toast.LENGTH_SHORT);
        toast.show();

    }

    public void displayToastMsg(View v) {

        toastMsg("Update Successful");

    }

    public void displayDeleteToastMsg(View v) {

        toastMsg("Successfully Deleted");

    }

}
