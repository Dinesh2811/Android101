package com.dinesh.android.java.database.sql.test;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

@SuppressLint("Range")
public class DatabaseHandler extends SQLiteOpenHelper {
    private final String TAG = "log_" + this.getClass().toString().split(this.getClass().getPackage().getName().split("\\.")[2] + ".")
            [this.getClass().toString().split(this.getClass().getPackage().getName().split("\\.")[2] + ".").length - 1];

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_NOTE = "note";
    private static final String COLUMN_LABEL = "label";
    private static final String COLUMN_CREATED_DATE = "createdDate";
    private static final String COLUMN_MODIFIED_DATE = "modifiedDate";
    private static final String COLUMN_NEW_SORTED_POSITION = "newSortedPosition";
//    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_NOTE + " TEXT, " + COLUMN_LABEL + " TEXT, " + COLUMN_CREATED_DATE + " TEXT, " + COLUMN_MODIFIED_DATE + " TEXT, " + COLUMN_NEW_SORTED_POSITION + " INTEGER)";
//    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
//    private static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
//    private static final String SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
//    private static final String SELECT_BY_TITLE = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ?";
//    private static final String SELECT_BY_TITLE_AND_LABEL = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_LABEL + " = ?";
//    private static final String UPDATE_BY_ID = "UPDATE " + TABLE_NAME + " SET " + COLUMN_TITLE + " = ?, " + COLUMN_NOTE + " = ?, " + COLUMN_LABEL + " = ?, " + COLUMN_CREATED_DATE + " = ?, " + COLUMN_MODIFIED_DATE + " = ?, " + COLUMN_NEW_SORTED_POSITION + " = ? WHERE " + COLUMN_ID + " = ?";
//    private static final String DELETE_BY_ID = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
//    private static final String DELETE_BY_TITLE = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ?";
//    private static final String DELETE_BY_TITLE_AND_LABEL = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_LABEL + " = ?";
//    private static final String DELETE_ALL = "DELETE FROM " + TABLE_NAME;
//    private static final String COUNT_ALL = "SELECT COUNT(*) FROM " + TABLE_NAME;
//    private static final String COUNT_BY_TITLE = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ?";
//    private static final String COUNT_BY_TITLE_AND_LABEL = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_LABEL + " = ?";
//    private static final String MAX_ID = "SELECT MAX(" + COLUMN_ID + ") FROM " + TABLE_NAME;
//    private static final String MIN_ID = "SELECT MIN(" + COLUMN_ID + ") FROM " + TABLE_NAME;
//    private static final String NEXT_ID = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " > ? ORDER BY " + COLUMN_ID + " ASC LIMIT 1";
//    private static final String PREVIOUS_ID = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " < ? ORDER BY " + COLUMN_ID + " DESC LIMIT 1";
//    private static final String NEXT_ID_BY_TITLE = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_ID + " > ? ORDER BY " + COLUMN_ID + " ASC LIMIT 1";
//    private static final String PREVIOUS_ID_BY_TITLE = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_ID + " < ? ORDER BY " + COLUMN_ID + " DESC LIMIT 1";
//    private static final String NEXT_ID_BY_TITLE_AND_LABEL = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_LABEL + " = ? AND " + COLUMN_ID + " > ? ORDER BY " + COLUMN_ID + " ASC LIMIT 1";
//    private static final String PREVIOUS_ID_BY_TITLE_AND_LABEL = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_LABEL + " = ? AND " + COLUMN_ID + " < ? ORDER BY " + COLUMN_ID + " DESC LIMIT 1";
//    private static final String MAX_NEW_SORTED_POSITION = "SELECT MAX(" + COLUMN_NEW_SORTED_POSITION + ") FROM " + TABLE_NAME;
//    private static final String MIN_NEW_SORTED_POSITION = "SELECT MIN(" + COLUMN_NEW_SORTED_POSITION + ") FROM " + TABLE_NAME;
//    private static final String UPDATE_NEW_SORTED_POSITION = "UPDATE " + TABLE_NAME + " SET " + COLUMN_NEW_SORTED_POSITION + " = ? WHERE " + COLUMN_ID + " = ?";
//    private static final String ALL_IDS = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " ASC";
//    private static final String ALL_IDS_BY_TITLE = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? ORDER BY " + COLUMN_ID + " ASC";
//    private static final String ALL_IDS_BY_TITLE_AND_LABEL = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_LABEL + " = ? ORDER BY " + COLUMN_ID + " ASC";
//    private static final String FIRST_ID = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " ASC LIMIT 1";
//    private static final String FIRST_ID_BY_TITLE = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? ORDER BY " + COLUMN_ID + " ASC LIMIT 1";
//    private static final String FIRST_ID_BY_TITLE_AND_LABEL = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_LABEL + " = ? ORDER BY " + COLUMN_ID + " ASC LIMIT 1";
//    private static final String LAST_ID = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1";
//    private static final String LAST_ID_BY_TITLE = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? ORDER BY " + COLUMN_ID + " DESC LIMIT 1";
//    private static final String LAST_ID_BY_TITLE_AND_LABEL = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_LABEL + " = ? ORDER BY " + COLUMN_ID + " DESC LIMIT 1";
//    private static final String NEXT_ID_BY_NEW_SORTED_POSITION = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_NEW_SORTED_POSITION + " > ? ORDER BY " + COLUMN_NEW_SORTED_POSITION + " ASC LIMIT 1";
//    private static final String PREVIOUS_ID_BY_NEW_SORTED_POSITION = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_NEW_SORTED_POSITION + " < ? ORDER BY " + COLUMN_NEW_SORTED_POSITION + " DESC LIMIT 1";
//    private static final String NEXT_ID_BY_NEW_SORTED_POSITION_AND_TITLE = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_NEW_SORTED_POSITION + " > ? ORDER BY " + COLUMN_NEW_SORTED_POSITION + " ASC LIMIT 1";
//    private static final String PREVIOUS_ID_BY_NEW_SORTED_POSITION_AND_TITLE = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_NEW_SORTED_POSITION + " < ? ORDER BY " + COLUMN_NEW_SORTED_POSITION + " DESC LIMIT 1";
//    private static final String HAS_ID = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
//    private static final String KEEP_ONLY_IDS = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " NOT IN (?)";
//    private static final String KEEP_ONLY_IDS_BY_TITLE = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_ID + " NOT IN (?)";
//    private static final String KEEP_ONLY_IDS_BY_TITLE_AND_LABEL = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_LABEL + " = ? AND " + COLUMN_ID + " NOT IN (?)";
//    private static final String WHERE_ID = "WHERE " + COLUMN_ID + " = ?";
//    private static final String EXISTS_ID = "SELECT COUNT(*) FROM " + TABLE_NAME + WHERE_ID;
//    private static final String EXISTS_ID_BY_TITLE = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_ID + " = ?";
//    private static final String EXISTS_ID_BY_TITLE_AND_LABEL = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_LABEL + " = ? AND " + COLUMN_ID + " = ?";
//    private static final String REMOVE_ID = "DELETE FROM " + TABLE_NAME + WHERE_ID;
//    private static final String REMOVE_ID_BY_TITLE = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_ID + " = ?";
//    private static final String REMOVE_ID_BY_TITLE_AND_LABEL = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_LABEL + " = ? AND " + COLUMN_ID + " = ?";
//    private static final String REMOVE_IDS = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " IN (?)";
//    private static final String TRUNCATE = "DELETE FROM " + TABLE_NAME;
//    private static final String TRUNCATE_BY_TITLE = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ?";
//    private static final String TRUNCATE_BY_TITLE_AND_LABEL = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_LABEL + " = ?";
//    private static final String INSERT = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_TITLE + ", " + COLUMN_LABEL + ", " + COLUMN_NEW_SORTED_POSITION + ") VALUES (?, ?, ?)";
//    private static final String INSERT_BY_TITLE = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_TITLE + ", " + COLUMN_LABEL + ", " + COLUMN_NEW_SORTED_POSITION + ") SELECT ?, ?, ? FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? ORDER BY " + COLUMN_NEW_SORTED_POSITION + " DESC LIMIT 1";
//    private static final String INSERT_BY_TITLE_AND_LABEL = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_TITLE + ", " + COLUMN_LABEL + ", " + COLUMN_NEW_SORTED_POSITION + ") SELECT ?, ?, ? FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + " = ? AND " + COLUMN_LABEL + " = ? ORDER BY " + COLUMN_NEW_SORTED_POSITION + " DESC LIMIT 1";
//    private static final String ORDER_BY_NEW_SORTED_POSITION = "ORDER BY " + COLUMN_NEW_SORTED_POSITION + " ASC";
//    private static final String ORDER_BY_NEW_SORTED_POSITION_AND_TITLE = "ORDER BY " + COLUMN_TITLE + ", " + COLUMN_NEW_SORTED_POSITION + " ASC";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_LABEL + " TEXT, " + COLUMN_NEW_SORTED_POSITION + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void add(String title, String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_LABEL, label);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void addNew(String title, String notes, String label, String createdDate, String modifiedDate, String newSortedPosition) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_NOTE, notes);
        values.put(COLUMN_LABEL, label);
        values.put(COLUMN_CREATED_DATE, createdDate);
        values.put(COLUMN_MODIFIED_DATE, modifiedDate);
        values.put(COLUMN_NEW_SORTED_POSITION, newSortedPosition);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void addNew(String title, String notes, String label, String createdDate, String modifiedDate, String newSortedPosition, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_NOTE, notes);
        values.put(COLUMN_LABEL, label);
        values.put(COLUMN_CREATED_DATE, createdDate);
        values.put(COLUMN_MODIFIED_DATE, modifiedDate);
        values.put(COLUMN_NEW_SORTED_POSITION, newSortedPosition);
        values.put(COLUMN_ID, id);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void getAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID, COLUMN_TITLE, COLUMN_LABEL, COLUMN_NEW_SORTED_POSITION }, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String label = cursor.getString(cursor.getColumnIndex(COLUMN_LABEL));
//            String newSortedPosition = cursor.getString(cursor.getColumnIndex(COLUMN_NEW_SORTED_POSITION));
//            Log.d("id", String.valueOf(id));
//            Log.d("title", title);
//            Log.d("label", label);
            Log.i(TAG, "getAll: "+id+" "+title+" "+label);
//            Log.d("newSortedPosition", newSortedPosition);
        }
        cursor.close();
        db.close();
    }

    public void getAllByTitle(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID, COLUMN_TITLE, COLUMN_LABEL, COLUMN_NEW_SORTED_POSITION }, COLUMN_TITLE + " = ?", new String[] { title }, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String titleString = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String labelString = cursor.getString(cursor.getColumnIndex(COLUMN_LABEL));
            String newSortedPosition = cursor.getString(cursor.getColumnIndex(COLUMN_NEW_SORTED_POSITION));
            Log.i(TAG, "getAllByLabel: "+id+" "+titleString+" "+labelString);
        }
        cursor.close();
        db.close();
    }


    public void getAllByLabel(String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID, COLUMN_TITLE, COLUMN_LABEL, COLUMN_NEW_SORTED_POSITION }, COLUMN_LABEL + " = ?", new String[] { label }, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String titleString = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String labelString = cursor.getString(cursor.getColumnIndex(COLUMN_LABEL));
            String newSortedPosition = cursor.getString(cursor.getColumnIndex(COLUMN_NEW_SORTED_POSITION));
            Log.i(TAG, "getAllByLabel: "+id+" "+titleString+" "+labelString);
            Log.d("newSortedPosition", newSortedPosition);
        }
        cursor.close();
        db.close();
    }

    public void getAllByLabelAndTitle(String label, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID, COLUMN_TITLE, COLUMN_LABEL, COLUMN_NEW_SORTED_POSITION }, COLUMN_LABEL + " = ? AND " + COLUMN_TITLE + " = ?", new String[] { label, title }, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String labelString = cursor.getString(cursor.getColumnIndex(COLUMN_LABEL));
            String newSortedPosition = cursor.getString(cursor.getColumnIndex(COLUMN_NEW_SORTED_POSITION));
            Log.d("id", String.valueOf(id));
            Log.d("label", labelString);
            Log.d("newSortedPosition", newSortedPosition);
        }
        cursor.close();
        db.close();
    }

    public void update(String title, String label, String newSortedPosition, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_LABEL, label);
        values.put(COLUMN_NEW_SORTED_POSITION, newSortedPosition);
        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[] { id });
        db.close();
    }

//    public void updateByTitle(String title, int newSortedPosition, String id) {
    public void updateByTitle(String title, String newTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, newTitle);
//        values.put(COLUMN_NEW_SORTED_POSITION, id);
        db.update(TABLE_NAME, values, COLUMN_TITLE + " = ?", new String[] { title });
//        db.update(TABLE_NAME, values, COLUMN_TITLE + " = ? OR " + COLUMN_ID + " = ?", new String[] { title, id });
        db.close();
    }

    public void updateByTitleAndLabel(String title, String label, String newSortedPosition, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_LABEL, label);
        values.put(COLUMN_NEW_SORTED_POSITION, newSortedPosition);
        db.update(TABLE_NAME, values, COLUMN_TITLE + " = ? AND " + COLUMN_LABEL + " = ?", new String[] { title, label });
        db.close();
    }

    public void delete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { id });
        db.close();
    }

    public void deleteByTitle(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_TITLE + " = ?", new String[] { title });
        db.close();
    }

    public void deleteByTitleAndLabel(String title, String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_TITLE + " = ? AND " + COLUMN_LABEL + " = ?", new String[] { title, label });
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }



    public void getCount() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID, COLUMN_TITLE, COLUMN_LABEL, COLUMN_NEW_SORTED_POSITION }, null, null, null, null, null);
        int count = cursor.getCount();
        Log.e(TAG, String.valueOf(count));
        cursor.close();
        db.close();
    }

    public void getCountByLabel(String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID, COLUMN_TITLE, COLUMN_LABEL, COLUMN_NEW_SORTED_POSITION }, COLUMN_LABEL + " = ?", new String[] { label }, null, null, null);
        int count = cursor.getCount();
        Log.d("count", String.valueOf(count));
        cursor.close();
        db.close();
    }

    public void getCountByLabelAndTitle(String label, String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID, COLUMN_TITLE, COLUMN_LABEL, COLUMN_NEW_SORTED_POSITION }, COLUMN_LABEL + " = ? AND " + COLUMN_TITLE + " = ?", new String[] { label, title }, null, null, null);
        int count = cursor.getCount();
        Log.d("count", String.valueOf(count));
        cursor.close();
        db.close();
    }


    public void vacuum() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("VACUUM");
        db.close();
    }

    public void backup() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("BACKUP TO '" + Environment.getExternalStorageDirectory().getPath() + "/backup.db'");
        db.close();
    }

    public void restore() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("RESTORE FROM '" + Environment.getExternalStorageDirectory().getPath() + "/backup.db'");
        db.close();
    }

}
