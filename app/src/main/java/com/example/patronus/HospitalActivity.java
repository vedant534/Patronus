package com.example.patronus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class HospitalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        final ArrayList<HospitalData> HospitalDatas = new ArrayList<HospitalData>();
        HospitalDatas.add(new HospitalData("Kota Heart", "25.150395173923922", "75.8525429404985"));
        HospitalDatas.add(new HospitalData("Metri Hospital", "25.148902789375224", "75.8526762291653"));
        HospitalDatas.add(new HospitalData("Sudha Hospital", "25.150596556086455", "75.85243558927155"));
        HospitalDatas.add(new HospitalData("K.K Pareek Hospital", "25.151730049711695", "75.83480982730215"));
        HospitalDatas.add(new HospitalData("Jaiswal Neurology Hospital", "25.15038266578935", "75.85315156852953"));
        HospitalDatas.add(new HospitalData("TT Hospital", "25.149728711997653", "75.8369146639858"));
        HospitalDatas.add(new HospitalData("Kota Trauma Hospital", "25.163115741985866", "75.8260434230024"));
        HospitalDatas.add(new HospitalData("Devanshi Hospital", "25.15295634623686", "75.84524907186088"));

        HospitalDataAdapter adapter = new HospitalDataAdapter(this,  HospitalDatas);

        ListView listView = (ListView) findViewById(R.id.hospital_list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HospitalData currHospital=HospitalDatas.get(position);
                String latitude=currHospital.getmLatitude();
                String longitude=currHospital.getmLongitude();

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr="+"25.2398826,75.7865667"+"&daddr="+latitude+","+longitude));
                startActivity(intent);
            }
        });

    }
}