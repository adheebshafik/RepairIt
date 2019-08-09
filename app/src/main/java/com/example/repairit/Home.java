package com.example.repairit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private CardView profileCard, vehicleCard, quotationCard, appointmentCard;
    private MenuItem aboutus, contactus, share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        profileCard = (CardView) findViewById(R.id.profile_card);
        vehicleCard = (CardView) findViewById(R.id.vehicle_card);
        quotationCard = (CardView) findViewById(R.id.quotation_card);
        appointmentCard = (CardView) findViewById(R.id.appointment_card);
        share = (MenuItem) findViewById(R.id.action_share);
        contactus =  (MenuItem) findViewById(R.id.action_contact);
        aboutus = (MenuItem) findViewById(R.id.action_about);


        //Adding onclick listener to the cards
        profileCard.setOnClickListener(this);
        vehicleCard.setOnClickListener(this);
        quotationCard.setOnClickListener(this);
        appointmentCard.setOnClickListener(this);





    }

    @Override
    public void onClick(View view) {

        Intent i;

        switch (view.getId()) {

            case R.id.profile_card: i = new Intent(this, Profile.class);
                                        startActivity(i);
                                        break;

            case R.id.vehicle_card: i = new Intent(this, Vehicle.class);
                                        startActivity(i);
                                        break;

            case R.id.quotation_card: i = new Intent(this, Quotation.class);
                                        startActivity(i);
                                        break;

            case R.id.appointment_card: i = new Intent(this, Appointment.class);
                                        startActivity(i);
                                        break;

            default:                    break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent i;

        switch (item.getItemId()) {

            case R.id.action_share:
                i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(i.EXTRA_TEXT, "Now get your vehicle fixed easily with RepairIt. Download it for free today.");
                this.startActivity(i);
                return true;

            case R.id.action_contact:
                i = new Intent(this, ContactUs.class);
                this.startActivity(i);
                return true;

            case R.id.action_about:
                i = new Intent(this, AboutUs.class);
                this.startActivity(i);
                return true;

            case R.id.action_logout:
                i = new Intent(this, LogIn.class);
                this.startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
