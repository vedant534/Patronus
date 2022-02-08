package com.example.patronus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HelplineActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<Contact> helplineContacts;
    LayoutInflater inflater ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);

        mRecyclerView = findViewById(R.id.helpline_listview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        helplineContacts = new ArrayList<>();
        getHelplineNumbers();

        HelplineAdapter helplineAdapter = new HelplineAdapter();
        mRecyclerView.setAdapter(helplineAdapter);

        //getHelplineNumbers();
        helplineAdapter.notifyDataSetChanged();
    }

    public class HelplineHolder extends RecyclerView.ViewHolder{
        TextView nameview,number;
        View rootview;

        public HelplineHolder(View view)
        {
            super(view);
            nameview = (TextView) view.findViewById(R.id.helpline_name_view);
            number = (TextView) view.findViewById(R.id.helpline_number_view);
            rootview = view.findViewById(R.id.helpline_contact_rootview);
        }

        public TextView getNameView()
        {
            return nameview;
        }
    }

    public class HelplineAdapter extends RecyclerView.Adapter<HelplineHolder>{


        @NonNull
        @Override
        public HelplineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = inflater.inflate(R.layout.helpline_contact_layout,null);
//            return new HelplineHolder(view);
//            LayoutInflater lf=getLayoutInflater();
//            View v=lf.inflate(R.layout.helpline_contact_layout,parent,false);
            //return new HelplineHolder(v);

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.helpline_contact_layout, parent, false);
            return new HelplineHolder(view);
        }

        @Override
        public void onBindViewHolder(HelplineActivity.HelplineHolder holder, int position) {
            Contact currentContact = helplineContacts.get(position);
            //holder.getNameView().setText(currentContact.getName());
            holder.nameview.setText(currentContact.getName());
            holder.number.setText(currentContact.getNumber());

            holder.rootview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+currentContact.getNumber()));
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return helplineContacts.size();
        }
    }

    public void getHelplineNumbers()
    {
        helplineContacts.add(new Contact("National Emergency number","112"));
        helplineContacts.add(new Contact("Disaster Management Services","108"));
        helplineContacts.add(new Contact("NDMA Disaster Management","1078"));
        helplineContacts.add(new Contact("NDRF Helpline","9711077372"));
        helplineContacts.add(new Contact("Road Accident Emergency Service","1073"));
        helplineContacts.add(new Contact("Railway Accident Emergencey Service","1072"));
        helplineContacts.add(new Contact("LPG Leak Helpline","1906"));
        helplineContacts.add(new Contact("Police Control Room","100"));
        helplineContacts.add(new Contact("Women's Helpline","181"));
        helplineContacts.add(new Contact("Anti Stalking Cell\n(New Delhi)","1091"));
        helplineContacts.add(new Contact("AIDS Helpline","1097"));
        helplineContacts.add(new Contact("Fire Control Room","101"));
        helplineContacts.add(new Contact("Senior Citizen Helpline","1291"));
        helplineContacts.add(new Contact("Centralised Accident & Trauma Services(CATS)","1099"));
        helplineContacts.add(new Contact("Indian Red-Cross Society\n(New Delhi)","011- 23711551"));
    }
}