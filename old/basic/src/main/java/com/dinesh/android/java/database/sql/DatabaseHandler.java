package com.dinesh.android.java.database.sql;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final String TAG = "log_" + this.getClass().toString().split(this.getClass().getPackage().getName().split("\\.")[2] + ".")
            [this.getClass().toString().split(this.getClass().getPackage().getName().split("\\.")[2] + ".").length - 1];

    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_NOTE = "note";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_NOTE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // TODO: 8/18/2022      Add data to SQLite database
    public void addData(Model model) {
        SQLiteDatabase db = this.getWritableDatabase();

        /**------------------------------------------------------Add data to SQLite database------------------------------------------------------//

         String query = "INSERT INTO " + TABLE_NAME + "(" +
         COLUMN_TITLE + ", " +
         COLUMN_NOTE + ") VALUES('" +
         model.getTitle() + "', '" +
         model.getNote() + "');";
         db.execSQL(query);
         db.close();

         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//

         ContentValues values = new ContentValues();
         values.put(COLUMN_TITLE, model.getTitle());
         values.put(COLUMN_NOTE, model.getNote());
         db.insert(TABLE_NAME, null, values);
         db.close();

         */

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, model.getTitle());
        values.put(COLUMN_NOTE, model.getNote());
        db.insert(TABLE_NAME, null, values);
        if (values != null) {
            Log.i(TAG, "addData: Data is added Successfully ----> "+values+ "\n"+getData(model));
        }
        db.close();
    }

    // TODO: 8/18/2022      Get data from SQLite database
    @SuppressLint("Range")
    public List<Model> getData(Model model) {
        List<Model> modelList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        /**------------------------------------------------------Get all the data from the table------------------------------------------------------//

         String query = "SELECT * FROM " + TABLE_NAME + ";";
         Cursor cursor = db.rawQuery(query, null);

         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//

         Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
         null, null, null, null, null);

         */


        /**------------------------------------------Get all the data from the table where the id is passed in------------------------------------------//

         String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "';";
         Cursor cursor = db.rawQuery(query, null);

         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//

         Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
         COLUMN_ID + "=?", new String[]{id}, null, null, null);

         */


        /**-----------------------------Get all the data from the table where the id is passed in and the title is passed in-----------------------------//

         String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_TITLE + " = '" + title + "';";
         Cursor cursor = db.rawQuery(query, null);

         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//

         Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
         COLUMN_ID + "=? AND " + COLUMN_TITLE + "=?",
         new String[]{id, title}, null, null, null);

         */

        /**---------------Get all the data from the table where the id is passed in and the title is passed in and the note is passed in---------------//

         String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_TITLE + " = '" + title + "' AND " + COLUMN_NOTE + " = '" + note + "';";
         Cursor cursor = db.rawQuery(query, null);

         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//

         Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
         COLUMN_ID + "=? AND " +
         COLUMN_TITLE + "=? AND " +
         COLUMN_NOTE + "=?",
         new String[]{id, title, note}, null, null, null);

         */

        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
                null, null, null, null, null);

        modelList.clear();
        if (cursor.moveToFirst()) {
            do {
////            Log.i(TAG, "ID: "+cursor.getString(0) + "   TITLE:" + cursor.getString(1) + "   NOTE:" + cursor.getString(2));
//            Log.i("getData", "ID: "+cursor.getString(cursor.getColumnIndex(COLUMN_ID)) +
//                    "   TITLE:" + cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)) +
//                    "   NOTE:" + cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)));

                modelList.add(new Model(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ID))));

            } while (cursor.moveToNext());
        }

//        while (cursor.moveToNext()) {
////            Log.i(TAG, "ID: "+cursor.getString(0) + "   TITLE:" + cursor.getString(1) + "   NOTE:" + cursor.getString(2));
//            Log.i(TAG, "ID: "+cursor.getString(cursor.getColumnIndex(COLUMN_ID)) + "   TITLE:" + cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)) + "   NOTE:" + cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)));
//        }

        cursor.close();
        db.close();
        return modelList;
    }


    // TODO: 8/18/2022      Update data in SQLite database
    public void updateDatabase(Model model) {
        SQLiteDatabase db = this.getWritableDatabase();

        /**---------------Update the data in the table where the id is passed in---------------//

         String updatequery = "UPDATE " + TABLE_NAME + " SET " +
         COLUMN_TITLE + " = '" + model.getTitle() + "', " +
         COLUMN_NOTE + " = '" + model.getNote() + "' WHERE " +
         COLUMN_ID + " = '" + model.getId() + "';";
         db.execSQL(updatequery);
         Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+updatequery+ "\n"+getData(model));

         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//

         ContentValues values = new ContentValues();
         values.put(COLUMN_TITLE, model.getTitle());
         values.put(COLUMN_NOTE, model.getNote() );
         db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[] { model.getId() });
         Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+values+ "\n"+getData(model));

         */


        /**---------------Update the data in the table where the id and the title is passed in---------------//

         String updatequery = "UPDATE " + TABLE_NAME + " SET " +
         COLUMN_TITLE + " = '" + model.getTitle() + "', " +
         COLUMN_NOTE + " = '" + model.getNote() + "' WHERE " +
         COLUMN_ID + " = '" + model.getId() + "' AND " +
         COLUMN_TITLE + " = '" + model.getTitle() + "';";
         db.execSQL(updatequery);
         Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+updatequery+ "\n"+getData(model));

         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//

         ContentValues values = new ContentValues();
         values.put(COLUMN_TITLE, model.getTitle());
         values.put(COLUMN_NOTE, model.getNote() );
         db.update(TABLE_NAME, values, COLUMN_ID + " = ? AND " + COLUMN_TITLE + " = ?", new String[] { model.getId(), model.getTitle() });
         Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+values+ "\n"+getData(model));

         */


        /**---------------Update the data in the table where the id and the title and the note is passed in---------------//

         String updatequery = "UPDATE " + TABLE_NAME + " SET " +
         COLUMN_TITLE + " = '" + model.getTitle() + "', " +
         COLUMN_NOTE + " = '" + model.getNote() + "' WHERE " +
         COLUMN_ID + " = '" + model.getId() + "' AND " +
         COLUMN_TITLE + " = '" + model.getTitle() + "' AND " +
         COLUMN_NOTE + " = '" + model.getNote() + "';";
         db.execSQL(updatequery);
         Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+updatequery+ "\n"+getData(model));

         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//

         ContentValues values = new ContentValues();
         values.put(COLUMN_TITLE, model.getTitle());
         values.put(COLUMN_NOTE, model.getNote() );
         db.update(TABLE_NAME, values, COLUMN_ID + " = ? AND " + COLUMN_TITLE + " = ? AND " + COLUMN_NOTE + " = ?", new String[] { model.getId(), model.getTitle(), model.getNote() });
         Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+values+ "\n"+getData(model));

         */

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, model.getTitle());
        values.put(COLUMN_NOTE, model.getNote() );
        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[] { model.getId() });
        Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+values+ "\n"+getData(model));

        db.close();
    }

    // TODO: 8/18/2022      Delete data from SQLite database
    public void deleteData(Model model) {
        SQLiteDatabase db = this.getWritableDatabase();


        /**-------------------------------------------------------------------Method 1-------------------------------------------------------------------//

         //Delete all the data from the table
         //        String deleteAllQuery = "DELETE FROM " + TABLE_NAME + ";";
         //        db.execSQL(deleteAllQuery);

         //Delete all the data from the table where the id is passed in
         String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.getId() + "';";
         db.execSQL(deleteQuery);

         //Delete all the data from the table where the id and the title is passed in
         //        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.getId() + "' AND " + COLUMN_TITLE + " = '" + model.getTitle() + "';";
         //        db.execSQL(deleteQuery);

         //Delete all the data from the table where the id and the title and the note is passed in
         //        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.getId() + "' AND " + COLUMN_TITLE + " = '" + model.getTitle() + "' AND " + COLUMN_NOTE + " = '" + model.getNote() + "';";
         //        db.execSQL(deleteQuery);

         Log.i(TAG, "deleteAllNotes: Data is updated Successfully ----> "+deleteQuery+ "\n"+getData(model));

         //-----------------------------------------------------------------------Method 2------------------------------------------------------------------------//

         //Delete all the data from the table
         //        db.delete(TABLE_NAME, null, null);

         //Delete all the data from the table where the id is passed in
         db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { model.getId() });

         //Delete all the data from the table where the id and the title is passed in
         //        db.delete(TABLE_NAME, COLUMN_ID + " = ? AND " + COLUMN_TITLE + " = ?", new String[] { model.getId(), model.getTitle() });

         //Delete all the data from the table where the id and the title and the note is passed in
         //        db.delete(TABLE_NAME, COLUMN_ID + " = ? AND " + COLUMN_TITLE + " = ? AND " + COLUMN_NOTE + " = ?", new String[] { model.getId(), model.getTitle(), model.getNote() });

         Log.i(TAG, "deleteAllNotes: Data is updated Successfully ----> "+getData(model));

         */

        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { model.getId() });

        Log.i(TAG, "deleteAllNotes: Data is updated Successfully ----> "+getData(model));

        db.close();
    }

    //Get count of the data in the table
    public int getCount(Model model) {
        SQLiteDatabase db = this.getReadableDatabase();

//        //Get count of the data in the table
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//        int count = cursor.getCount();
//
//        //Get count of the data in the table where the id is passed in
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.id + "';", null);
//        int count = cursor.getCount();
//
//        //Get count of the data in the table where the id and the title is passed in
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.id + "' AND " + COLUMN_TITLE + " = '" + model.title + "';", null);
//        int count = cursor.getCount();
//
//        //Get count of the data in the table where the id and the title and the note is passed in
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.id + "' AND " + COLUMN_TITLE + " = '" + model.title + "' AND " + COLUMN_NOTE + " = '" + model.note + "';", null);
//        int count = cursor.getCount();


        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        int count = cursor.getCount();

        Log.i(TAG, "getCount: ----> "+count);
        cursor.close();
        return count;
    }


}


/*


    public void addData(Model model) {
        SQLiteDatabase db = this.getWritableDatabase();

        /**------------------------------------------------------Add data to SQLite database------------------------------------------------------//

                 String query = "INSERT INTO " + TABLE_NAME + "(" +
                         COLUMN_TITLE + ", " +
                         COLUMN_NOTE + ") VALUES('" +
                         model.getTitle() + "', '" +
                         model.getNote() + "');";
                 db.execSQL(query);
                 db.close();

         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//

                 ContentValues values = new ContentValues();
                 values.put(COLUMN_TITLE, model.getTitle());
                 values.put(COLUMN_NOTE, model.getNote());
                 db.insert(TABLE_NAME, null, values);
                 db.close();

         */
/*


    ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, model.getTitle());
                values.put(COLUMN_NOTE, model.getNote());
                db.insert(TABLE_NAME, null, values);
                if (values != null) {
                Log.i(TAG, "addData: Data is added Successfully ----> "+values+ "\n"+getData(model));
                }
                db.close();
                }


 */


/*


    public void getData() {
        SQLiteDatabase db = this.getWritableDatabase();

        /**------------------------------------------------------Get all the data from the table------------------------------------------------------//

                 String query = "SELECT * FROM " + TABLE_NAME + ";";
                 Cursor cursor = db.rawQuery(query, null);

         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//

                 Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
                         null, null, null, null, null);

         */


//Get all the data from the table
//        String query = "SELECT * FROM " + TABLE_NAME + ";";
//        Cursor cursor = db.rawQuery(query, null);

//        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
//                null, null, null, null, null);
//





/**------------------------------------------Get all the data from the table where the id is passed in------------------------------------------//

 String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "';";
 Cursor cursor = db.rawQuery(query, null);

 //-----------------------------------------------------------------------OR------------------------------------------------------------------------//

 Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
 COLUMN_ID + "=?", new String[]{id}, null, null, null);

 */

//Get all the data from the table where the id is passed in
//        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "';";
//        Cursor cursor = db.rawQuery(query, null);

//        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
//                COLUMN_ID + "=?", new String[]{id}, null, null, null);





/**-----------------------------Get all the data from the table where the id is passed in and the title is passed in-----------------------------//

 String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_TITLE + " = '" + title + "';";
 Cursor cursor = db.rawQuery(query, null);

 //-----------------------------------------------------------------------OR------------------------------------------------------------------------//

 Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
 COLUMN_ID + "=? AND " + COLUMN_TITLE + "=?",
 new String[]{id, title}, null, null, null);

 */

//Get all the data from the table where the id is passed in and the title is passed in
//        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_TITLE + " = '" + title + "';";
//        Cursor cursor = db.rawQuery(query, null);

//        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
//                COLUMN_ID + "=? AND " + COLUMN_TITLE + "=?",
//                new String[]{id, title}, null, null, null);





/**---------------Get all the data from the table where the id is passed in and the title is passed in and the note is passed in---------------//

 String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_TITLE + " = '" + title + "' AND " + COLUMN_NOTE + " = '" + note + "';";
 Cursor cursor = db.rawQuery(query, null);

 //-----------------------------------------------------------------------OR------------------------------------------------------------------------//

 Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
 COLUMN_ID + "=? AND " +
 COLUMN_TITLE + "=? AND " +
 COLUMN_NOTE + "=?",
 new String[]{id, title, note}, null, null, null);

 */

//Get all the data from the table where the id is passed in and the title is passed in and the note is passed in
//        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "' AND " + COLUMN_TITLE + " = '" + title + "' AND " + COLUMN_NOTE + " = '" + note + "';";
//        Cursor cursor = db.rawQuery(query, null);

//        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE},
//                COLUMN_ID + "=? AND " +
//                        COLUMN_TITLE + "=? AND " +
//                        COLUMN_NOTE + "=?",
//                new String[]{id, title, note}, null, null, null);



/*

        if (cursor.moveToFirst()) {
                do {
//            Log.i(TAG, "ID: "+cursor.getString(0) + "   TITLE:" + cursor.getString(1) + "   NOTE:" + cursor.getString(2));
                Log.i(TAG, "ID: "+cursor.getString(cursor.getColumnIndex(COLUMN_ID)) +
                "   TITLE:" + cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)) +
                "   NOTE:" + cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)));
                } while (cursor.moveToNext());
                }

//        while (cursor.moveToNext()) {
////            Log.i(TAG, "ID: "+cursor.getString(0) + "   TITLE:" + cursor.getString(1) + "   NOTE:" + cursor.getString(2));
//            Log.i(TAG, "ID: "+cursor.getString(cursor.getColumnIndex(COLUMN_ID)) + "   TITLE:" + cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)) + "   NOTE:" + cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)));
//        }
                cursor.close();
                db.close();
                }


 */








/*


    public void updateDatabase(Model model) {
        SQLiteDatabase db = this.getWritableDatabase();

        /**---------------Update the data in the table where the id is passed in---------------//

                 String updatequery = "UPDATE " + TABLE_NAME + " SET " +
                         COLUMN_TITLE + " = '" + model.getTitle() + "', " +
                         COLUMN_NOTE + " = '" + model.getNote() + "' WHERE " +
                         COLUMN_ID + " = '" + model.getId() + "';";
                 db.execSQL(updatequery);
                 Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+updatequery+ "\n"+getData(model));

         //-----------------------------------------------------------------------OR------------------------------------------------------------------------//

                 ContentValues values = new ContentValues();
                 values.put(COLUMN_TITLE, model.getTitle());
                 values.put(COLUMN_NOTE, model.getNote() );
                 db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[] { model.getId() });
                 Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+values+ "\n"+getData(model));

         */


/**---------------Update the data in the table where the id and the title is passed in---------------//

 String updatequery = "UPDATE " + TABLE_NAME + " SET " +
 COLUMN_TITLE + " = '" + model.getTitle() + "', " +
 COLUMN_NOTE + " = '" + model.getNote() + "' WHERE " +
 COLUMN_ID + " = '" + model.getId() + "' AND " +
 COLUMN_TITLE + " = '" + model.getTitle() + "';";
 db.execSQL(updatequery);
 Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+updatequery+ "\n"+getData(model));

 //-----------------------------------------------------------------------OR------------------------------------------------------------------------//

 ContentValues values = new ContentValues();
 values.put(COLUMN_TITLE, model.getTitle());
 values.put(COLUMN_NOTE, model.getNote() );
 db.update(TABLE_NAME, values, COLUMN_ID + " = ? AND " + COLUMN_TITLE + " = ?", new String[] { model.getId(), model.getTitle() });
 Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+values+ "\n"+getData(model));

 */


/**---------------Update the data in the table where the id and the title and the note is passed in---------------//

 String updatequery = "UPDATE " + TABLE_NAME + " SET " +
 COLUMN_TITLE + " = '" + model.getTitle() + "', " +
 COLUMN_NOTE + " = '" + model.getNote() + "' WHERE " +
 COLUMN_ID + " = '" + model.getId() + "' AND " +
 COLUMN_TITLE + " = '" + model.getTitle() + "' AND " +
 COLUMN_NOTE + " = '" + model.getNote() + "';";
 db.execSQL(updatequery);
 Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+updatequery+ "\n"+getData(model));

 //-----------------------------------------------------------------------OR------------------------------------------------------------------------//

 ContentValues values = new ContentValues();
 values.put(COLUMN_TITLE, model.getTitle());
 values.put(COLUMN_NOTE, model.getNote() );
 db.update(TABLE_NAME, values, COLUMN_ID + " = ? AND " + COLUMN_TITLE + " = ? AND " + COLUMN_NOTE + " = ?", new String[] { model.getId(), model.getTitle(), model.getNote() });
 Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+values+ "\n"+getData(model));

 */

    /*

    ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, model.getTitle());
                values.put(COLUMN_NOTE, model.getNote() );
                db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[] { model.getId() });
                Log.i(TAG, "updateDatabase: Data is updated Successfully ----> "+values+ "\n"+getData(model));

                db.close();
                }

*/




/*


    public void delteData(Model model) {
        SQLiteDatabase db = this.getWritableDatabase();


        /**-------------------------------------------------------------------Method 1-------------------------------------------------------------------//

                 //Delete all the data from the table
         //        String deleteAllQuery = "DELETE FROM " + TABLE_NAME + ";";
         //        db.execSQL(deleteAllQuery);

                 //Delete all the data from the table where the id is passed in
                 String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.getId() + "';";
                 db.execSQL(deleteQuery);

                 //Delete all the data from the table where the id and the title is passed in
         //        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.getId() + "' AND " + COLUMN_TITLE + " = '" + model.getTitle() + "';";
         //        db.execSQL(deleteQuery);

                 //Delete all the data from the table where the id and the title and the note is passed in
         //        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.getId() + "' AND " + COLUMN_TITLE + " = '" + model.getTitle() + "' AND " + COLUMN_NOTE + " = '" + model.getNote() + "';";
         //        db.execSQL(deleteQuery);

                 Log.i(TAG, "deleteAllNotes: Data is updated Successfully ----> "+deleteQuery+ "\n"+getData(model));

         //-----------------------------------------------------------------------Method 2------------------------------------------------------------------------//

                 //Delete all the data from the table
         //        db.delete(TABLE_NAME, null, null);

                 //Delete all the data from the table where the id is passed in
                 db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { model.getId() });

                 //Delete all the data from the table where the id and the title is passed in
         //        db.delete(TABLE_NAME, COLUMN_ID + " = ? AND " + COLUMN_TITLE + " = ?", new String[] { model.getId(), model.getTitle() });

                 //Delete all the data from the table where the id and the title and the note is passed in
         //        db.delete(TABLE_NAME, COLUMN_ID + " = ? AND " + COLUMN_TITLE + " = ? AND " + COLUMN_NOTE + " = ?", new String[] { model.getId(), model.getTitle(), model.getNote() });

                 Log.i(TAG, "deleteAllNotes: Data is updated Successfully ----> "+getData(model));

         */

//
//        //Delete all the data from the table
////        String deleteAllQuery = "DELETE FROM " + TABLE_NAME + ";";
////        db.execSQL(deleteAllQuery);
//
//        //Delete all the data from the table where the id is passed in
//        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.getId() + "';";
//        db.execSQL(deleteQuery);
//
//        //Delete all the data from the table where the id and the title is passed in
////        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.getId() + "' AND " + COLUMN_TITLE + " = '" + model.getTitle() + "';";
////        db.execSQL(deleteQuery);
//
//        //Delete all the data from the table where the id and the title and the note is passed in
////        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + model.getId() + "' AND " + COLUMN_TITLE + " = '" + model.getTitle() + "' AND " + COLUMN_NOTE + " = '" + model.getNote() + "';";
////        db.execSQL(deleteQuery);
//
//        Log.i(TAG, "deleteAllNotes: Data is updated Successfully ----> "+deleteQuery+ "\n"+getData(model));


//        //Delete all the data from the table
////        db.delete(TABLE_NAME, null, null);
//
//        //Delete all the data from the table where the id is passed in
//        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { model.getId() });
//
//        //Delete all the data from the table where the id and the title is passed in
////        db.delete(TABLE_NAME, COLUMN_ID + " = ? AND " + COLUMN_TITLE + " = ?", new String[] { model.getId(), model.getTitle() });
//
//        //Delete all the data from the table where the id and the title and the note is passed in
////        db.delete(TABLE_NAME, COLUMN_ID + " = ? AND " + COLUMN_TITLE + " = ? AND " + COLUMN_NOTE + " = ?", new String[] { model.getId(), model.getTitle(), model.getNote() });

/*
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { model.getId() });

//        Log.i(TAG, "deleteAllNotes: Data is updated Successfully ----> "+getData(model));

                db.close();
                }

 */





