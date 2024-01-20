package com.dinesh.android.java.database.sql.test;

public class GetCount {
    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_NOTE = "note";




//    public int getCount(Model model) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
////        //Get count of the data in the table
////        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
////        int count = cursor.getCount();
////
////        //Get count of the data in the table where the id is passed in
////        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.id + "';", null);
////        int count = cursor.getCount();
////
////        //Get count of the data in the table where the id and the title is passed in
////        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.id + "' AND " + COLUMN_TITLE + " = '" + model.title + "';", null);
////        int count = cursor.getCount();
////
////        //Get count of the data in the table where the id and the title and the note is passed in
////        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.id + "' AND " + COLUMN_TITLE + " = '" + model.title + "' AND " + COLUMN_NOTE + " = '" + model.note + "';", null);
////        int count = cursor.getCount();
//
//
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//        int count = cursor.getCount();
//
//        Log.i(TAG, "getCount: ----> "+count);
//        cursor.close();
//        return count;
//    }

}
