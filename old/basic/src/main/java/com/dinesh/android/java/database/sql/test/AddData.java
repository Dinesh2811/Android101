package com.dinesh.android.java.database.sql.test;

public class AddData {

    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_NOTE = "note";


//    public void addData(Model model) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        /**------------------------------------------------------Add data to SQLite database------------------------------------------------------//
//
//         String query = "INSERT INTO " + TABLE_NAME + "(" +
//         COLUMN_TITLE + ", " +
//         COLUMN_NOTE + ") VALUES('" +
//         model.getTitle() + "', '" +
//         model.getNote() + "');";
//         db.execSQL(query);
//         db.close();
//
//         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//
//
//         ContentValues values = new ContentValues();
//         values.put(COLUMN_TITLE, model.getTitle());
//         values.put(COLUMN_NOTE, model.getNote());
//         db.insert(TABLE_NAME, null, values);
//         db.close();
//
//         */
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_TITLE, model.getTitle());
//        values.put(COLUMN_NOTE, model.getNote());
//        db.insert(TABLE_NAME, null, values);
//        if (values != null) {
//            Log.i(TAG, "addData: Data is added Successfully ----> "+values+ "\n"+getData(model));
//        }
//        db.close();
//    }




}
