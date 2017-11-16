package ru.samsung.itschool.contentprovider1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by d.yacenko on 08.11.17.
 */

class SchoolDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = SchoolDbHelper.class.getSimpleName();

    /**
     * Имя файла базы данных
     */
    private static final String DATABASE_NAME = "school.db";

    /**
     * Версия базы данных. При изменении схемы увеличить на единицу
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Конструктор {@link SchoolDbHelper}.
     *
     * @param context Контекст приложения
     */
    public SchoolDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Вызывается при создании базы данных
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Строка для создания таблицы
        String SQL_CREATE_GUESTS_TABLE = "CREATE TABLE " + SchoolContract.StudentEntry.TABLE_NAME + " ("
                + SchoolContract.StudentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SchoolContract.StudentEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + SchoolContract.StudentEntry.COLUMN_GENDER + " INTEGER NOT NULL DEFAULT 3, "
                + SchoolContract.StudentEntry.COLUMN_ENROLL_ID + " INTEGER NOT NULL DEFAULT 0);";

        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_GUESTS_TABLE);
    }

    /**
     * Вызывается при обновлении схемы базы даннных
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
