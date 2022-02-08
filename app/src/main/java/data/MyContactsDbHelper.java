package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import data.MyContactsContract.MyContactsEntry;

public class MyContactsDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = "MyContactsDbHelper.java";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "patronus_contacts.db";

    public static final String SQL_CREATE_MYCONTACTS_TABLE = "CREATE TABLE " + MyContactsEntry.TABLE_NAME + " (" + MyContactsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + MyContactsEntry.COLUMN_NAME + " TEXT NOT NULL," + MyContactsEntry.COLUMN_NUMBER + " TEXT NOT NULL )" ;

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MyContactsEntry.TABLE_NAME;

    public MyContactsDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        Log.v(LOG_TAG,"database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(LOG_TAG,"table created");
        db.execSQL(SQL_CREATE_MYCONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
