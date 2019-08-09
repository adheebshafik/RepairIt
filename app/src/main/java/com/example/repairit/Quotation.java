package com.example.repairit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Quotation extends AppCompatActivity {


    Button newQuoBtn;
    TextView quo1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        quo1 = findViewById(R.id.viewQuo1TextView);
        newQuoBtn = findViewById(R.id.newQuoBtn);

        quo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Quotation.this, UpdateQuotation.class);
                startActivity(intent);
                finish();
            }
        });


        newQuoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Quotation.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddQuotation(), null).commit();
                Intent intent = new Intent(Quotation.this, AddQuotation.class);
                startActivity(intent);
                finish();
            }
        });

        }


}

