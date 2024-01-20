package com.dinesh.android.java.database.sql;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "log_" + this.getClass().toString().split(this.getClass().getPackage().getName().split("\\.")[2] + ".")
            [this.getClass().toString().split(this.getClass().getPackage().getName().split("\\.")[2] + ".").length - 1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Save the data using shared preferences
        SharedPreferences saveData = getSharedPreferences("DataBase", MODE_PRIVATE);
        SharedPreferences.Editor editor = saveData.edit();
        editor.putString("language", "English");
        editor.commit();        //editor.apply();




        //Get the shared preferences data
        SharedPreferences getData = getSharedPreferences("DataBase", MODE_PRIVATE);
        String userLanguage = getData.getString("language", "En");
//        Log.e(TAG, "Get the shared preferences data: ----> "+userLanguage );


        DatabaseHandler db = new DatabaseHandler(this);
        Model model = new Model();
        List<Model> modelList = db.getData(model);
        if (db.getCount(model) == 0) {
            for (int i = 0; i < 10; i++) {
                Model addDummyData = new Model("Title " + i, "Note " + i, "0");
                db.addData(addDummyData);
            }
        }

//        db.addData(model);
//        db.getData(model);
//        db.updateDatabase(model);
//        db.deleteData(model);
        db.getCount(model);


//        Log.e(TAG, "unsorted: "+modelList );

        /**------------------------------------------------------Sort by Comparator------------------------------------------------------//

         Log.e(TAG, "unsorted: "+modelList );

         Collections.sort(modelList, new Comparator<Model>() {
        @Override
        public int compare(Model o1, Model o2) {
        //Ascending order
        //                return o1.getId().compareTo(o2.getId());
        //                return o1.getNote().compareTo(o2.getNote());
        //                return o1.getTitle().compareTo(o2.getTitle());

        //Descending order
        return o2.getId().compareTo(o1.getId());
        //                return o2.getTitle().compareTo(o1.getTitle());
        //                return o2.getNote().compareTo(o1.getNote());
        }
        });

         Log.i(TAG, "  sorted: "+modelList );

         //--------------------------------------------------------------Sort by Comparable---------------------------------------------------------------//

         //        Collections.sort(modelList);

         //public class Model implements Comparable<Model> {

        @Override
        public int compareTo(Model o) {
        //Ascending order
        //        return (String.valueOf(this.id)).compareTo(String.valueOf(o.id));
        //        return this.getId().compareTo(o.getId());
        //        return this.getTitle().compareTo(o.getTitle());
        //        return this.getNote().compareTo(o.getNote());


        //Descending order
        //        return o.getId().compareTo(this.getId());
        //        return o.getTitle().compareTo(this.getTitle());
        return o.getNote().compareTo(this.getNote());
        }

        //    }

         Log.i(TAG, "  sorted: "+modelList );

         */


//        //--------------------------------------------------------------Sort by Comparator---------------------------------------------------------------//
//        Collections.sort(modelList, new Comparator<Model>() {
//            @Override
//            public int compare(Model o1, Model o2) {
//                //Ascending order
////                return o1.getId().compareTo(o2.getId());
////                return o1.getNote().compareTo(o2.getNote());
////                return o1.getTitle().compareTo(o2.getTitle());
//
//                //Descending order
//                return o2.getId().compareTo(o1.getId());
////                return o2.getTitle().compareTo(o1.getTitle());
////                return o2.getNote().compareTo(o1.getNote());
//            }
//        });
//        //-------------------------------------------------------ArrayList is Sorted by Comparator--------------------------------------------------------//



//        //--------------------------------------------------------------Sort by Comparable---------------------------------------------------------------//
////        Collections.sort(modelList);
//        //-------------------------------------------------------ArrayList is Sorted by Comparable--------------------------------------------------------//



//        Log.i(TAG, "  sorted: "+modelList );


        //Converting Date to String and String to Date
        String dateAsString = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        Date date = null;
        try {
            date=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(dateAsString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}