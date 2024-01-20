package com.dinesh.android.java.api.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static volatile MySingleton INSTANCE;
    private RequestQueue requestQueue;

    public MySingleton() {
    }

    public static MySingleton getInstance() {
        if (INSTANCE == null) {
            synchronized(MySingleton.class){
                if (INSTANCE == null) {
                    INSTANCE = new MySingleton();
                }
            }
        }
        return INSTANCE;
    }

    public RequestQueue getRequestQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

}
