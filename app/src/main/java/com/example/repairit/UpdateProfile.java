package com.example.repairit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UpdateProfile extends AppCompatActivity {

    Button update, delete, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        update = findViewById(R.id.userProfileUpdateBtn);
        delete = findViewById(R.id.userProfileDeleteBtn);
        back = findViewById(R.id.userProfileBackBtn);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayToastMsg(view);
                Intent intent = new Intent(UpdateProfile.this, Profile.class);
                startActivity(intent);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDeleteToastMsg(view);
                Intent intent = new Intent(UpdateProfile.this, LogIn.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateProfile.this, Profile.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(UpdateProfile.this, msg, Toast.LENGTH_SHORT);
        toast.show();

    }

    public void displayToastMsg(View v) {
        toastMsg("Update Successful");
    }

    public void displayDeleteToastMsg(View v) {

        toastMsg("Successfully Deleted");

    }

}







