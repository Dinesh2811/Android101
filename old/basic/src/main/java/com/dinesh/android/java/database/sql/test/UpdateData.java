package com.dinesh.android.java.database.sql.test;

public class UpdateData {
    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_NOTE = "note";




//    public void updateDatabase(Model model) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        /**---------------Update the data in the table where the id is passed in---------------//
//
//         String updatequery = "UPDATE " + TABLE_NAME + " SET " +
//         COLUMN_TITLE + " = '" + model.getTitle() + "', " +
//         COLUMN_NOTE + " = '" + model.getNote() + "' WHERE " +
//         COLUMN_ID + " = '" + model.getId() + "';";
//         db.execSQL(updatequery);
//         Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+updatequery+ "\n"+getData(model));
//
//         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//
//
//         ContentValues values = new ContentValues();
//         values.put(COLUMN_TITLE, model.getTitle());
//         values.put(COLUMN_NOTE, model.getNote() );
//         db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[] { model.getId() });
//         Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+values+ "\n"+getData(model));
//
//         */
//
//
//        /**---------------Update the data in the table where the id and the title is passed in---------------//
//
//         String updatequery = "UPDATE " + TABLE_NAME + " SET " +
//         COLUMN_TITLE + " = '" + model.getTitle() + "', " +
//         COLUMN_NOTE + " = '" + model.getNote() + "' WHERE " +
//         COLUMN_ID + " = '" + model.getId() + "' AND " +
//         COLUMN_TITLE + " = '" + model.getTitle() + "';";
//         db.execSQL(updatequery);
//         Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+updatequery+ "\n"+getData(model));
//
//         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//
//
//         ContentValues values = new ContentValues();
//         values.put(COLUMN_TITLE, model.getTitle());
//         values.put(COLUMN_NOTE, model.getNote() );
//         db.update(TABLE_NAME, values, COLUMN_ID + " = ? AND " + COLUMN_TITLE + " = ?", new String[] { model.getId(), model.getTitle() });
//         Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+values+ "\n"+getData(model));
//
//         */
//
//
//        /**---------------Update the data in the table where the id and the title and the note is passed in---------------//
//
//         String updatequery = "UPDATE " + TABLE_NAME + " SET " +
//         COLUMN_TITLE + " = '" + model.getTitle() + "', " +
//         COLUMN_NOTE + " = '" + model.getNote() + "' WHERE " +
//         COLUMN_ID + " = '" + model.getId() + "' AND " +
//         COLUMN_TITLE + " = '" + model.getTitle() + "' AND " +
//         COLUMN_NOTE + " = '" + model.getNote() + "';";
//         db.execSQL(updatequery);
//         Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+updatequery+ "\n"+getData(model));
//
//         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//
//
//         ContentValues values = new ContentValues();
//         values.put(COLUMN_TITLE, model.getTitle());
//         values.put(COLUMN_NOTE, model.getNote() );
//         db.update(TABLE_NAME, values, COLUMN_ID + " = ? AND " + COLUMN_TITLE + " = ? AND " + COLUMN_NOTE + " = ?", new String[] { model.getId(), model.getTitle(), model.getNote() });
//         Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+values+ "\n"+getData(model));
//
//         */
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_TITLE, model.getTitle());
//        values.put(COLUMN_NOTE, model.getNote() );
//        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[] { model.getId() });
//        Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+values+ "\n"+getData(model));
//
//        db.close();
//    }






}
