package com.dinesh.android.java.database.sql.test;

public class GetData {
    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_NOTE = "note";




//    public List<Model> getData(Model model) {
//        List<Model> modelList = new ArrayList<>();
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        /**------------------------------------------------------Get all the data from the table------------------------------------------------------//
//
//         String query = "SELECT * FROM " + TABLE_NAME + ";";
//         Cursor cursor = db.rawQuery(query, null);
//
//         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//
//
//         Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
//         null, null, null, null, null);
//
//         */
//
//
//        /**------------------------------------------Get all the data from the table where the id is passed in------------------------------------------//
//
//         String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "';";
//         Cursor cursor = db.rawQuery(query, null);
//
//         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//
//
//         Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
//         COLUMN_ID + "=?", new String[]{id}, null, null, null);
//
//         */
//
//
//        /**-----------------------------Get all the data from the table where the id is passed in and the title is passed in-----------------------------//
//
//         String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_TITLE + " = '" + title + "';";
//         Cursor cursor = db.rawQuery(query, null);
//
//         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//
//
//         Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
//         COLUMN_ID + "=? AND " + COLUMN_TITLE + "=?",
//         new String[]{id, title}, null, null, null);
//
//         */
//
//        /**---------------Get all the data from the table where the id is passed in and the title is passed in and the note is passed in---------------//
//
//         String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_TITLE + " = '" + title + "' AND " + COLUMN_NOTE + " = '" + note + "';";
//         Cursor cursor = db.rawQuery(query, null);
//
//         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//
//
//         Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
//         COLUMN_ID + "=? AND " +
//         COLUMN_TITLE + "=? AND " +
//         COLUMN_NOTE + "=?",
//         new String[]{id, title, note}, null, null, null);
//
//         */
//
//        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
//                null, null, null, null, null);
//
//        modelList.clear();
//        if (cursor.moveToFirst()) {
//            do {
//////            Log.i(TAG, "ID: "+cursor.getString(0) + "   TITLE:" + cursor.getString(1) + "   NOTE:" + cursor.getString(2));
////            Log.i("getData", "ID: "+cursor.getString(cursor.getColumnIndex(COLUMN_ID)) +
////                    "   TITLE:" + cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)) +
////                    "   NOTE:" + cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)));
//
//                modelList.add(new Model(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
//
//            } while (cursor.moveToNext());
//        }
//
////        while (cursor.moveToNext()) {
//////            Log.i(TAG, "ID: "+cursor.getString(0) + "   TITLE:" + cursor.getString(1) + "   NOTE:" + cursor.getString(2));
////            Log.i(TAG, "ID: "+cursor.getString(cursor.getColumnIndex(COLUMN_ID)) + "   TITLE:" + cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)) + "   NOTE:" + cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)));
////        }
//
//        cursor.close();
//        db.close();
//        return modelList;
//    }







}
