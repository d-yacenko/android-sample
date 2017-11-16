package ru.samsung.itschool.book.contentprovider;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
String[] projectionFields = new String[] { ContactsContract.Contacts._ID,
    	               ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER };
        CursorLoader cl= new CursorLoader(
                MainActivity.this,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projectionFields,
                null,
                null,
                null
        );
        Log.d("ContentProvider","create ");
        return  cl;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d("ContentProvider","finished "+cursor.getCount());
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String str = textView.getText().toString() + "\n" + cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)) + ":"
                    + cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))+":"+cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            textView.setText(str);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
