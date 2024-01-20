package com.dinesh.android.kotlin.retrofit.sample

import android.view.View
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.R
import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException

private val TAG = "log_" + SampleMain::class.java.name.split(SampleMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class SampleMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiCall()
    }

    private fun apiCall() {
/*

        https://api.api-ninjas.com/v1/geocoding?city=Aurangabad&country=india

        header --> x-api-key  ZeXPuXEB2NaJu7YJggN+rg==TY6W83MoejjYOSZv

*/

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.api-ninjas.com/v1/geocoding?city=Aurangabad&country=india")
            .header("x-api-key", "ZeXPuXEB2NaJu7YJggN+rg==TY6W83MoejjYOSZv")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "onFailure: ${e.message}")
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                try {
                    val jsonArray = JSONArray(responseBody)
                    if (jsonArray.length() > 0) {
                        val jsonObject = jsonArray.getJSONObject(0)
                        val latitude = jsonObject.getDouble("latitude")
                        val longitude = jsonObject.getDouble("longitude")
                        runOnUiThread {
                            Log.i(TAG, "onResponse: latitude --> $latitude      longitude --> $longitude")
                        }
                    }
                } catch (e: JSONException) {
                    Log.e(TAG, "onResponse: Error parsing JSON array ${e.message}")
                    e.printStackTrace()
                }
            }
        })
    }
}