package com.example.patronus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

import data.MyContactsContract;
import data.MyContactsCursorAdapter;
import data.MyContactsDbHelper;
import data.MyContactsContract.MyContactsEntry;

public class ContactsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    String number,name;
    //ArrayList<Contact> contactsArrayList = new ArrayList<>();
    //ContactsAdapter adapter;

    static final int REQUEST_SELECT_CONTACT = 1;
    static final int REQUEST_SELECT_PHONE_NUMBER = 1;

    private MyContactsDbHelper mDbHelper;

    MyContactsCursorAdapter myContactsCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        mDbHelper = new MyContactsDbHelper(this);
        //displayeDatabaseInfo();
        //getSavedContacts();
        //SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //adapter = new ContactsAdapter(this,contactsArrayList);


        myContactsCursorAdapter = new MyContactsCursorAdapter(this,null);
        ListView rootView = (ListView) findViewById(R.id.contactslist_view);
        getSupportLoaderManager().initLoader(0, null, this);
        rootView.setAdapter(myContactsCursorAdapter);
        LoadContacts();

        Button addContact = (Button) findViewById(R.id.addcontact_button);
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_SELECT_CONTACT);
                Log.v("ContactsActivity","succesful");
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("ContactsActivity.java","Contact Recieved");
        if(requestCode == REQUEST_SELECT_CONTACT){
            Log.v("onActivityResult","succesful");
            if(resultCode == RESULT_OK){

                Log.v("ContactsActivity","succesful");
                Uri contactData = data.getData();
                Cursor cursor =  managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();

                Log.v("ContactsActivity","succesful");

                number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                name=   cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                //contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));

                Log.v("ContactsActivity",name);
                Log.v("ContactsActivity",number);

                saveMyContact(name,number);

                //contactsArrayList.add(new Contact(name,number));
                //adapter.add(new Contact(name,number));
                //adapter.notifyDataSetChanged();
                //displayeDatabaseInfo();
            }
        }
    }

    void saveMyContact(String name,String number)
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyContactsEntry.COLUMN_NAME,name);
        values.put(MyContactsEntry.COLUMN_NUMBER,number);

        Uri uri = getContentResolver().insert(MyContactsEntry.CONTENT_URI,values);
        if(uri != null)
            Toast.makeText(this,"Contact saved",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Not able to save contact",Toast.LENGTH_SHORT).show();

        LoadContacts();
    }

    public void LoadContacts()
    {
        Cursor cursor = getContentResolver().query(MyContactsEntry.CONTENT_URI,null,null,null,null);
        myContactsCursorAdapter.swapCursor(cursor);
    }

//    void displayeDatabaseInfo()
//    {
//        SQLiteDatabase db = mDbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + MyContactsContract.MyContactsEntry.TABLE_NAME,null);
//
//        try {
//            TextView textView = (TextView) findViewById(R.id.contacts_count_view);
//            textView.setText("" + cursor.getCount());
//        }
//        finally {
//            cursor.close();
//        }
//    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, MyContactsEntry.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        myContactsCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        myContactsCursorAdapter.swapCursor(null);
    }
}