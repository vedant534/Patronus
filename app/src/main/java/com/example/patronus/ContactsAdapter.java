package com.example.patronus;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.zip.Inflater;

import data.MyContactsContract;

public class ContactsAdapter extends ArrayAdapter<Contact> {

    public ContactsAdapter(Activity context, ArrayList<Contact> contactArrayList)
    {
        super(context,0,contactArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemvView = convertView;
        if(listItemvView == null)
        {
            listItemvView = LayoutInflater.from(getContext()).inflate(
                    R.layout.contact_layout, parent, false);
        }

        TextView nameTextView = (TextView) listItemvView.findViewById(R.id.name_textview);
        TextView numberTextView = (TextView) listItemvView.findViewById(R.id.number_textview);
        TextView contactnumberTextView = (TextView) listItemvView.findViewById(R.id.contactnumber_view);

        Contact currentContact = getItem(position);

        nameTextView.setText(currentContact.getName());
        numberTextView.setText(currentContact.getNumber());
        contactnumberTextView.setText("Contact " + (position+1));

        View deleteButton = (View) listItemvView.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(currentContact);
                deleteFromDatabase(position);
                notifyDataSetChanged();

            }
        });

        numberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + currentContact.getNumber()));
                getContext().startActivity(intent);
            }
        });

        return listItemvView;
    }

    void deleteFromDatabase(int position)
    {
        Uri uri = ContentUris.withAppendedId(MyContactsContract.MyContactsEntry.CONTENT_URI,position);
        getContext().getContentResolver().delete(uri,null,null);
    }
}
