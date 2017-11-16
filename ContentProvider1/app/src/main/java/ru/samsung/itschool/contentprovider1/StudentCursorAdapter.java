package ru.samsung.itschool.contentprovider1;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by d.yacenko on 11.11.17.
 */

class StudentCursorAdapter extends CursorAdapter {
    /**
     * Constructs a new {@link StudentCursorAdapter}.
     *
     * @param context The context
     * @param cursor  The cursor from which to get the data.
     */
    public StudentCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);

        // Find the columns of guest attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(SchoolContract.StudentEntry.COLUMN_NAME);
        int enrollColumnIndex = cursor.getColumnIndex(SchoolContract.StudentEntry.COLUMN_ENROLL_ID);

        // Read the guest attributes from the Cursor for the current guest
        String guestName = cursor.getString(nameColumnIndex);
        String guestCity = cursor.getString(enrollColumnIndex);

        // If the city is empty string or null, then use some default text
        // that says "Unknown", so the TextView isn't blank.
        if (TextUtils.isEmpty(guestCity)) {
            guestCity = "unknown";
        }

        // Update the TextViews with the attributes for the current guest
        nameTextView.setText(guestName);
        summaryTextView.setText(guestCity);
    }
}
