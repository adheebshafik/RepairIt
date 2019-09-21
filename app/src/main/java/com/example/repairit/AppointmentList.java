package com.example.repairit;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AppointmentList extends ArrayAdapter<AppointmentObject> {

    private Activity context;
    private List<AppointmentObject> appointmentList;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_appointment_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.tvAppName);

        AppointmentObject appointmentObject = appointmentList.get(position);

        textViewName.setText(appointmentObject.getVehicleNo());

        return listViewItem;

    }

    public AppointmentList(Activity context, List<AppointmentObject> ql) {
        super(context, R.layout.layout_appointment_list, ql);
        this.context = context;
        this.appointmentList = ql;
    }

}
