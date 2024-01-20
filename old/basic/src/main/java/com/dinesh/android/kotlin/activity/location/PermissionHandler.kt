package com.dinesh.android.kotlin.activity.location

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar


class PermissionHandler(private val activity: AppCompatActivity, private val permissionCallback: PermissionCallback) {
    private val TAG = "log_" + PermissionHandler::class.java.name.split(PermissionHandler::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    private lateinit var requestMultiplePermissionsLauncher: ActivityResultLauncher<Array<String>>

    private var permissionsToRequest: Array<String> = when(Build.VERSION.SDK_INT){
        34 -> {
            arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.NEARBY_WIFI_DEVICES,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.FOREGROUND_SERVICE,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                Manifest.permission.FOREGROUND_SERVICE_LOCATION
            )
        }
        Build.VERSION_CODES.TIRAMISU -> {
            arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.NEARBY_WIFI_DEVICES,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.FOREGROUND_SERVICE,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
        Build.VERSION_CODES.Q -> {
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.FOREGROUND_SERVICE,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
        Build.VERSION_CODES.P -> {
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.FOREGROUND_SERVICE
            )
        }
        else -> {
            if (Build.VERSION.SDK_INT > 34){
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.NEARBY_WIFI_DEVICES,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.FOREGROUND_SERVICE,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                    Manifest.permission.FOREGROUND_SERVICE_LOCATION
                )
            } else{
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }

    private var currentPermissionIndex = 0
    private val deniedPermissions = mutableListOf<String>()

    init {
        initializePermissionLauncher()
    }

    private fun initializePermissionLauncher() {
        requestMultiplePermissionsLauncher =
            activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                handlePermissionResult(permissions)
            }
    }

    fun requestPermissions() {
        currentPermissionIndex = 0
        deniedPermissions.clear()
        requestNextPermission()
    }

    private fun requestNextPermission() {
        if (currentPermissionIndex < permissionsToRequest.size) {
            val permission = permissionsToRequest[currentPermissionIndex]
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                requestMultiplePermissionsLauncher.launch(arrayOf(permission))
            } else {
                currentPermissionIndex++
                requestNextPermission()
            }
        } else {
            Log.i(TAG, "All permissions requested --> ${currentPermissionIndex}")
            permissionCallback.onAllPermissionsGranted()
        }
    }

    private fun handlePermissionResult(permissions: Map<String, Boolean>) {
        val allPermissionsGranted = permissions.all { it.value }
        if (allPermissionsGranted) {
            // All permissions are granted, request the next one
            currentPermissionIndex++
            requestNextPermission()
        } else {
            // Handle denied permissions
            permissions.filterNot { it.value }.forEach { (permission, isDenied) ->
                if (isPermissionPermanentlyDenied(permission)) {
                    showPermissionSnackbar()
                } else {
                    // Re-request denied permissions
                    deniedPermissions.add(permission)
                }
            }
            requestDeniedPermissions()
        }
    }

    private fun isPermissionPermanentlyDenied(permission: String): Boolean {
        return !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }

    private fun requestDeniedPermissions() {
        if (deniedPermissions.isNotEmpty()) {
            val permissionsToRequest = deniedPermissions.toTypedArray()
            requestMultiplePermissionsLauncher.launch(permissionsToRequest)
            deniedPermissions.clear()
        }
    }

    private fun showPermissionSnackbar() {
        Snackbar.make(activity.findViewById(android.R.id.content), "Some permissions are permanently denied. Please enable them in the device settings.",
            Snackbar.LENGTH_LONG).show()
    }
}
