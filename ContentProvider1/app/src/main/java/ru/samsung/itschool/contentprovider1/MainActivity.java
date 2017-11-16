package ru.samsung.itschool.contentprovider1;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    /**
     * Адаптер для ListView
     */
    StudentCursorAdapter mCursorAdapter;
    private static final int STUDENT_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        ListView guestListView = (ListView) findViewById(R.id.list);
        guestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);

                // Form the content URI that represents the specific guest that was clicked on,
                // by appending the "id" (passed as input to this method) onto the
                // {@link GuestEntry#CONTENT_URI}.
                // For example, the URI would be "content://com.example.android.guests/guests/2"
                // if the guest with ID 2 was clicked on.
                Uri currentGuestUri = ContentUris.withAppendedId(SchoolContract.StudentEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentGuestUri);

                // Launch the {@link EditorActivity} to display the data for the current guest.
                startActivity(intent);
            }
        });

        // Если список пуст
        View emptyView = findViewById(R.id.empty_view);
        guestListView.setEmptyView(emptyView);

        // Адаптер
        // Пока данных нет используем null
        mCursorAdapter = new StudentCursorAdapter(this, null);
        guestListView.setAdapter(mCursorAdapter);


        getLoaderManager().initLoader(STUDENT_LOADER, null, this);
    }
 @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_new_data:
                insertStudent();
                return true;
            case R.id.action_delete_all_entries:
                deleteAllStudent();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllStudent() {
        int rowsDeleted = getContentResolver().delete(SchoolContract.StudentEntry.CONTENT_URI, null, null);
        mCursorAdapter.notifyDataSetChanged();
        Log.v("MainActivity", rowsDeleted + " rows deleted from school database");
    }

    private void insertStudent() {
        ContentValues values = new ContentValues();
        values.put(SchoolContract.StudentEntry.COLUMN_NAME, "Иванов");
        values.put(SchoolContract.StudentEntry.COLUMN_GENDER, SchoolContract.StudentEntry.GENDER_MALE);
        values.put(SchoolContract.StudentEntry.COLUMN_ENROLL_ID, 7);
        Uri newUri = getContentResolver().insert(SchoolContract.StudentEntry.CONTENT_URI, values);
        mCursorAdapter.notifyDataSetChanged();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Зададим нужные колонки
        String[] projection = {
                SchoolContract.StudentEntry._ID,
                SchoolContract.StudentEntry.COLUMN_NAME,
                SchoolContract.StudentEntry.COLUMN_ENROLL_ID};
        // Загрузчик запускает запрос ContentProvider в фоновом потоке
        return new CursorLoader(this,
                SchoolContract.StudentEntry.CONTENT_URI,   // URI контент-провайдера для запроса
                projection,             // колонки, которые попадут в результирующий курсор
                null,                   // без условия WHERE
                null,                   // без аргументов
                null);                  // сортировка по умолчанию
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
  // Обновляем CursorAdapter новым курсором, которые содержит обновленные данные
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
 // Освобождаем ресурсы
        mCursorAdapter.swapCursor(null);
    }
}
