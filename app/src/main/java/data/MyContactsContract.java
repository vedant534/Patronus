package data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class MyContactsContract {

    public static final String LOG_TAG = "MyContactsContract.java";

    public static final String CONTENT_AUTHORITY = "com.example.patronus";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MYCONTACTS = "mycontacts";

    private MyContactsContract()
    {

    }

    public static final class MyContactsEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_MYCONTACTS);
        public static final String TABLE_NAME = "mycontacts";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_NUMBER = "number";
    }
}
