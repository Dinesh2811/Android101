package com.dinesh.android.java.api.volley;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dinesh.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "log_" + this.getClass().toString().split(this.getClass().getPackage().getName().split("\\.")[2] + ".")
            [this.getClass().toString().split(this.getClass().getPackage().getName().split("\\.")[2] + ".").length - 1];

    //https://jsonplaceholder.typicode.com/todos/1
    private RequestQueue requestQueue;
    String jsonObjectRequest_url = "https://jsonplaceholder.typicode.com/todos/1";
    String jsonArrayRequest_url = "https://jsonplaceholder.typicode.com/todos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //requestQueue = Volley.newRequestQueue(this);
        requestQueue = MySingleton.getInstance().getRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                jsonObjectRequest_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, "onResponse: jsonObjectRequest ->" + response.getString("title"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: jsonObjectRequest ->" + error.getMessage());

            }
        });


        //JSonArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                jsonArrayRequest_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < 5; i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Log.d(TAG, "onResponse: jsonArrayRequest ->"
                                + jsonObject.getString("id") +
                                " " + jsonObject.getString("title"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: jsonArrayRequest ->" + error.getMessage());

            }
        });

        requestQueue.add(jsonArrayRequest);
        requestQueue.add(jsonObjectRequest);
    }
}
