package com.dinesh.android.kotlin.activity.location

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity(), PermissionCallback {
    private val TAG = "log_" + MainActivity::class.java.name.split(MainActivity::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    private var locationServiceIntent: Intent? = null
    private lateinit var permissionHandler: PermissionHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAllPermissionsGranted() {
        startLocationService()
    }

    private fun startLocationService() {
        locationServiceIntent = Intent(applicationContext, LocationService::class.java)

        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        Log.i(TAG, "isGpsEnabled --> $isGpsEnabled        isNetworkEnabled --> $isNetworkEnabled")
        if(!isGpsEnabled && !isNetworkEnabled) {
            Log.e(TAG, "startLocationService: GPS is disabled")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            locationServiceIntent?.let { ContextCompat.startForegroundService(this, it) }
        } else {
            locationServiceIntent?.let { startService(it) }
        }
    }

    private fun stopLocationService() {
        locationServiceIntent?.let {
            stopService(it)
        }
    }

    override fun onStart() {
        super.onStart()
//        startLocationService()
        permissionHandler = PermissionHandler(this, this)
        permissionHandler.requestPermissions()
    }

//    override fun onStop() {
//        super.onStop()
//        stopLocationService()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        stopLocationService()
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.i(TAG, "onBackPressed: ")
        stopLocationService()
    }

}