package com.example.patronus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class HospitalDataAdapter extends ArrayAdapter<HospitalData> {
    public HospitalDataAdapter(Context context, ArrayList<HospitalData>HospitalDatas) {
        super(context, 0,HospitalDatas);
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View listItemView=convertView;
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        HospitalData currentHospital=getItem(position);

        ImageView plus=(ImageView)listItemView.findViewById(R.id.plus);
        plus.setImageResource(R.drawable.plus2);
        TextView HospitalName=(TextView)listItemView.findViewById(R.id.hospital_name);
        HospitalName.setText(currentHospital.getmHospitalName());
        return listItemView;
    }
}
