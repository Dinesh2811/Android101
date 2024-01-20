package com.dinesh.android.kotlin.activity.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority


class LocationManager(private var context: Context, private var timeInterval: Long, private var minimalDistance: Float) {
    private val TAG = "log_" + LocationManager::class.java.name.split(LocationManager::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    private var locationRequest: LocationRequest
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private var locationCallback: LocationCallback

    init {
        locationRequest = createRequest()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.lastOrNull()?.let { location ->
//                    Log.e(TAG, "onLocationResult: ${location}")
                }
                locationResult ?: return
                for (location in locationResult.locations) {
                    Toast.makeText(context, "Location: ${location.latitude}, ${location.longitude}", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Location: ${location.latitude}, ${location.longitude}")
                }
            }
        }

    }

    private fun createRequest(): LocationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, timeInterval).apply {
//            setMinUpdateDistanceMeters(minimalDistance)
        setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
        setWaitForAccurateLocation(true)
    }.build()

    @SuppressLint("MissingPermission")
    fun startLocationTracking() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    fun stopLocationTracking() {
        fusedLocationClient.flushLocations()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun changeRequest(timeInterval: Long, minimalDistance: Float) {
        this.timeInterval = timeInterval
        this.minimalDistance = minimalDistance
        createRequest()
        stopLocationTracking()
        startLocationTracking()
    }
}