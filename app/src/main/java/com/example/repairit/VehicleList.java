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

public class VehicleList extends ArrayAdapter<VehicleObject> {

    private Activity context;
    private List<VehicleObject> vehicleList;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_vehicle_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.tvVehicleName);

        VehicleObject vehicleObject = vehicleList.get(position);

        textViewName.setText(vehicleObject.getVehicleNo());

        return listViewItem;

    }

    public VehicleList(Activity context, List<VehicleObject> ql) {
        super(context, R.layout.layout_vehicle_list, ql);
        this.context = context;
        this.vehicleList = ql;
    }

}
