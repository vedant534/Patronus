package data;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.patronus.R;

public class MyContactsCursorAdapter extends CursorAdapter {
    public MyContactsCursorAdapter(Context context, Cursor cursor)
    {
        super(context,cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.contact_layout,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameTextView = (TextView) view.findViewById(R.id.name_textview);
        TextView numberTextView = (TextView) view.findViewById(R.id.number_textview);

        int nameColumnIndex = cursor.getColumnIndex(MyContactsContract.MyContactsEntry.COLUMN_NAME);
        int numberColumnIndex = cursor.getColumnIndex(MyContactsContract.MyContactsEntry.COLUMN_NUMBER);
        int idColumnIndex = cursor.getColumnIndex(MyContactsContract.MyContactsEntry._ID);

        String name = cursor.getString(nameColumnIndex);
        String number = cursor.getString(numberColumnIndex);
        long id = Integer.parseInt(cursor.getString(idColumnIndex));

        Log.v("MyContactsCursorAdapter",name);

        nameTextView.setText(name);
        numberTextView.setText(number);

        numberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                v.getContext().startActivity(intent);
            }
        });

        ImageView deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = ContentUris.withAppendedId(MyContactsContract.MyContactsEntry.CONTENT_URI,id);
                v.getContext().getContentResolver().delete(uri,null,null);
            }
        });
    }
}
