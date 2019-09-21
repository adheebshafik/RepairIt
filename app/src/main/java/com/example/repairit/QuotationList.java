package com.example.repairit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class QuotationList extends ArrayAdapter<QuotationObject> {

    private Activity context;
    private List<QuotationObject> quotationList;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_quotation_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.tvName);

        QuotationObject quotationObject = quotationList.get(position);

        textViewName.setText(quotationObject.getVehicleNo());

        return listViewItem;

    }

    public QuotationList(Activity context, List<QuotationObject> quotationList) {
        super(context, R.layout.layout_quotation_list, quotationList);
        this.context = context;
        this.quotationList= quotationList;
    }
}
