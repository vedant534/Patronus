package data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import data.MyContactsContract.MyContactsEntry;

public class MyContactsProvider extends ContentProvider {

    public static final String LOG_TAG = MyContactsProvider.class.getSimpleName();

    private MyContactsDbHelper mDbHelper;
    private static final int MYCONTACTS = 100;
    private static final int MYCONTACTS_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(MyContactsContract.CONTENT_AUTHORITY,MyContactsContract.PATH_MYCONTACTS,MYCONTACTS);
        sUriMatcher.addURI(MyContactsContract.CONTENT_AUTHORITY,MyContactsContract.PATH_MYCONTACTS + "/#",MYCONTACTS_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new MyContactsDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;
        final int match = sUriMatcher.match(uri);

        switch (match){
            case MYCONTACTS:
                cursor = db.query(MyContactsEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case MYCONTACTS_ID:
                selection = MyContactsEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(MyContactsEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case MYCONTACTS:
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                long id = db.insert(MyContactsEntry.TABLE_NAME,null,values);
                if(id == -1)
                {
                    Log.e(LOG_TAG,"Failed to insert row for " + uri);
                    return null;
                }

                getContext().getContentResolver().notifyChange(uri,null);

                return ContentUris.withAppendedId(uri,id);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match  = sUriMatcher.match(uri);
        int rowsDeleted = 0;

        switch (match)
        {
            case MYCONTACTS:
                rowsDeleted = db.delete(MyContactsEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case MYCONTACTS_ID:
                selection = MyContactsEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};

                rowsDeleted = db.delete(MyContactsEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Delete is not supported for " + uri);
        }

        if(rowsDeleted != 0)
            getContext().getContentResolver().notifyChange(uri,null);

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
