package ru.samsung.itschool.contentprovider1;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by d.yacenko on 10.11.17.
 */

public class EditorActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private EditText mNameEditText;
    private EditText mEnrollEditText;

    private Spinner mGenderSpinner;

    private int mGender = 2;

    /**
     * Content URI for the existing student (null if it's a new student)
     */
    private Uri mCurrentStudentUri;

    /**
     * Identifier for the student data loader
     */
    private static final int EXISTING_STUDENT_LOADER = 0;

    /**
     * Boolean flag that keeps track of whether the student has been edited (true) or not (false)
     */
    private boolean mStudentHasChanged = false;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mStudentHasChanged boolean to true.
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mStudentHasChanged = true;
            return false;
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        mCurrentStudentUri = intent.getData();

        // If the intent DOES NOT contain a student content URI, then we know that we are
        // creating a new pet.
        if (mCurrentStudentUri == null) {
            setTitle("Новый ученки");
            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a student that hasn't been created yet.)
            invalidateOptionsMenu();
        } else {
            setTitle("Изменение данных");
            // Initialize a loader to read the student data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_STUDENT_LOADER, null, this);
        }

        mNameEditText = (EditText) findViewById(R.id.edit_student_name);
        mEnrollEditText = (EditText) findViewById(R.id.edit_student_enroll);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);

        mNameEditText.setOnTouchListener(mTouchListener);
        mEnrollEditText.setOnTouchListener(mTouchListener);
        mGenderSpinner.setOnTouchListener(mTouchListener);

        setupSpinner();

    }

    /**
     * Настраиваем spinner для выбора пола у студента.
     */
    private void setupSpinner() {

        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mGenderSpinner.setAdapter(genderSpinnerAdapter);
        mGenderSpinner.setSelection(2);

        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_female))) {
//                        mGender = 0; // Ж
                        mGender = SchoolContract.StudentEntry.GENDER_FEMALE; // Кошка
                    } else if (selection.equals(getString(R.string.gender_male))) {
//                        mGender = 1; // М
                        mGender = SchoolContract.StudentEntry.GENDER_MALE; // Кот
                    } else {
//                        mGender = 2; // Не определен
                        mGender = SchoolContract.StudentEntry.GENDER_UNKNOWN; // Не определен
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 2; // Unknown
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new student, hide the "Delete" menu item.
        if (mCurrentStudentUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
//                insertStudent();
                saveStudent();
                // Закрываем активность
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (MainActivity)
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
                if (!mStudentHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        // If the pet hasn't changed, continue with handling back button press
        if (!mStudentHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

  
    /**
     * Получаем данные и сохраняем гостя в базе данных
     */
    private void saveStudent() {

        // Считываем данные из текстовых полей
        String name = mNameEditText.getText().toString().trim();
        String enroll = mEnrollEditText.getText().toString().trim();
//        int age = Integer.parseInt(ageString);

        // Check if this is supposed to be a new student
        // and check if all the fields in the editor are blank
        if (mCurrentStudentUri == null &&
                TextUtils.isEmpty(name) && TextUtils.isEmpty(enroll) &&
                mGender == SchoolContract.StudentEntry.GENDER_UNKNOWN) {
            // Since no fields were modified, we can return early without creating a new student.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and student attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(SchoolContract.StudentEntry.COLUMN_NAME, name);
        values.put(SchoolContract.StudentEntry.COLUMN_ENROLL_ID, enroll);
        values.put(SchoolContract.StudentEntry.COLUMN_GENDER, mGender);
//        values.put(StudentEntry.COLUMN_AGE, age);



        // Determine if this is a new or existing student by checking if mCurrentStudentUri is null or not
        if (mCurrentStudentUri == null) {
            // This is a NEW student, so insert a new student into the provider,
            // returning the content URI for the new student.
            Uri newUri = getContentResolver().insert(SchoolContract.StudentEntry.CONTENT_URI, values);

            if (newUri == null) {
                // Если null, значит ошибка при вставке.
                Toast.makeText(this, "Ошибка при заведении гостя", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Гость заведён успешно",
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            // Otherwise this is an EXISTING student, so update the student with content URI: mCurrentStudentUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentGuetUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentStudentUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, "Ошибка при редактировании гостя", Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, "Данные исправлены успешно",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Show a dialog that warns the user there are unsaved changes that will be lost
     * if they continue leaving the editor.
     *
     * @param discardButtonClickListener is the click listener for what to do when
     *                                   the user confirms they want to discard their changes
     */
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Since the editor shows all pet attributes, define a projection that contains
        // all columns from the pet table
        String[] projection = {
                SchoolContract.StudentEntry._ID,
                SchoolContract.StudentEntry.COLUMN_NAME,
                SchoolContract.StudentEntry.COLUMN_ENROLL_ID,
                SchoolContract.StudentEntry.COLUMN_GENDER};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentStudentUri,         // Query the content URI for the current student
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of student attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(SchoolContract.StudentEntry.COLUMN_NAME);
            int enrollColumnIndex = cursor.getColumnIndex(SchoolContract.StudentEntry.COLUMN_ENROLL_ID);
            int genderColumnIndex = cursor.getColumnIndex(SchoolContract.StudentEntry.COLUMN_GENDER);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            String enroll = cursor.getString(enrollColumnIndex);
            int gender = cursor.getInt(genderColumnIndex);

            // Update the views on the screen with the values from the database
            mNameEditText.setText(name);
            mEnrollEditText.setText(enroll);

            // Gender is a dropdown spinner, so map the constant value from the database
            // into one of the dropdown options (0 is Female, 1 is Male, 2 is Unknown).
            // Then call setSelection() so that option is displayed on screen as the current selection.
            switch (gender) {
                case SchoolContract.StudentEntry.GENDER_MALE:
                    mGenderSpinner.setSelection(1);
                    break;
                case SchoolContract.StudentEntry.GENDER_FEMALE:
                    mGenderSpinner.setSelection(0);
                    break;
                default:
                    mGenderSpinner.setSelection(2);
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        mNameEditText.setText("");
        mEnrollEditText.setText("");
        mGenderSpinner.setSelection(2); // Select "Unknown" gender
    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the student.
                deleteStudent();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the student.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the pet in the database.
     */
    private void deleteStudent() {
        // Only perform the delete if this is an existing student.
        if (mCurrentStudentUri != null) {
            // Call the ContentResolver to delete the student at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentStudentUri
            // content URI already identifies the student that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentStudentUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, "Ошибка при удалении гостя",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Гость успешно удален",
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Закрываем активность
        finish();
    }
}
