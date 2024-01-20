package com.dinesh.android.java.database.sql.test;

public class DeleteData {
    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_NOTE = "note";




//    public void deleteData(Model model) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//
//        /**-------------------------------------------------------------------Method 1-------------------------------------------------------------------//
//
//         //Delete all the data from the table
//         //        String deleteAllQuery = "DELETE FROM " + TABLE_NAME + ";";
//         //        db.execSQL(deleteAllQuery);
//
//         //Delete all the data from the table where the id is passed in
//         String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.getId() + "';";
//         db.execSQL(deleteQuery);
//
//         //Delete all the data from the table where the id and the title is passed in
//         //        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.getId() + "' AND " + COLUMN_TITLE + " = '" + model.getTitle() + "';";
//         //        db.execSQL(deleteQuery);
//
//         //Delete all the data from the table where the id and the title and the note is passed in
//         //        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.getId() + "' AND " + COLUMN_TITLE + " = '" + model.getTitle() + "' AND " + COLUMN_NOTE + " = '" + model.getNote() + "';";
//         //        db.execSQL(deleteQuery);
//
//         Log.i(TAG, "deleteAllNotes: Data is updated Successfully ----> "+deleteQuery+ "\n"+getData(model));
//
//         //-----------------------------------------------------------------------Method 2------------------------------------------------------------------------//
//
//         //Delete all the data from the table
//         //        db.delete(TABLE_NAME, null, null);
//
//         //Delete all the data from the table where the id is passed in
//         db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { model.getId() });
//
//         //Delete all the data from the table where the id and the title is passed in
//         //        db.delete(TABLE_NAME, COLUMN_ID + " = ? AND " + COLUMN_TITLE + " = ?", new String[] { model.getId(), model.getTitle() });
//
//         //Delete all the data from the table where the id and the title and the note is passed in
//         //        db.delete(TABLE_NAME, COLUMN_ID + " = ? AND " + COLUMN_TITLE + " = ? AND " + COLUMN_NOTE + " = ?", new String[] { model.getId(), model.getTitle(), model.getNote() });
//
//         Log.i(TAG, "deleteAllNotes: Data is updated Successfully ----> "+getData(model));
//
//         */
//
//        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { model.getId() });
//
//        Log.i(TAG, "deleteAllNotes: Data is updated Successfully ----> "+getData(model));
//
//        db.close();
//    }



}
