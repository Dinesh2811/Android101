package com.dinesh.android.app

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dinesh.android.R

private val TAG = "log_" + RequestPermission::class.java.name.split(RequestPermission::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

//open class RequestPermission(private val permissionsToRequest: Array<String> = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)): BaseActivity() {
//open class RequestPermission(private val permissionsToRequest: Array<String>): BaseActivity() {
//open class RequestPermission(private var permissionsToRequest: Array<String> = arrayOf()): BaseActivity() {
open class RequestPermission(): BaseActivity() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Array<String>>
    private var permissionList = ArrayList<String>()
    private var permanentlyDeniedPermissionList = ArrayList<String>()
    private var grantedPermission = true

    private var permissionsToRequest: Array<String> = arrayOf()
    constructor(permissionsToRequestArray: Array<String>): this(){
        this.permissionsToRequest = permissionsToRequestArray
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewLayout(R.layout.activity_main)
        if (permissionsToRequest.isEmpty()){
            permissionsToRequest = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS).requestedPermissions
                .filter { it.startsWith("android.permission.") }
                .filterNot { it == Manifest.permission.NEARBY_WIFI_DEVICES || it == Manifest.permission.MANAGE_EXTERNAL_STORAGE || it == Manifest.permission.READ_PHONE_NUMBERS }
                .toTypedArray()
        }
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), ::onPermissionsResult)
    }

    override fun onStart() {
        super.onStart()
        if (permissionsToRequest.isNotEmpty()){
            checkPermission()
        }
    }

    private fun appLogic() {
        if (isAllPermissionGranted()) {
            // All permissions have been granted
        } else{
            // Not All permissions are granted
            alertDialogForPermanentlyDeniedPermissions()
        }
    }

    private fun checkPermission(permissions: Array<String>) {
        activityResultLauncher.launch(permissions)
    }

    private fun checkPermission() {
        permissionList.clear()
        permanentlyDeniedPermissionList.clear()
        grantedPermission = true
        permissionsToRequest.forEach { permission ->
            when {
                ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED -> {
                    Log.d(TAG, "$permission already granted")
                }
                ActivityCompat.shouldShowRequestPermissionRationale(this, permission) -> {
                    Log.d(TAG, "$permission has been previously denied, request it again")
                    permissionList.add(permission)
                    grantedPermission = false
                }
                permanentlyDeniedPermissionList.contains(permission) -> {
                    Log.e(TAG, "$permission has been permanently denied, do nothing")
                }
                else -> {
                    Log.d(TAG, "$permission not yet granted, add it to the permissionList")
                    permissionList.add(permission)
                    grantedPermission = false
                }
            }
        }

        if (!grantedPermission && !permanentlyDeniedPermissionList.containsAll(permissionList)) {
            Log.d(TAG, "Requesting permissions: $permissionList")
            checkPermission(permissionList.toTypedArray())
        } else {
            // All permissions are already granted or permanently denied
            Log.e(TAG, "checkPermission ----> All permissions are already granted or permanently denied")
            Log.i(TAG, "permissionList ----> $permissionList")
            Log.i(TAG, "permanentlyDeniedPermissionList ----> $permanentlyDeniedPermissionList")
            // Proceed with your app logic here
            appLogic()
        }

    }

    private fun onPermissionsResult(result: Map<String, Boolean>) {
        result.forEach { (permission, isGranted) ->
            if (!isGranted) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    Log.e(TAG, "$permission has been permanently denied, add it to the permanentlyDeniedPermissionList")
                    if (!permanentlyDeniedPermissionList.contains(permission)){
                        permanentlyDeniedPermissionList.add(permission)
                    }
                }
                grantedPermission = false
            }
        }
        if (!grantedPermission && !permanentlyDeniedPermissionList.containsAll(permissionList)) {
            // Some permissions are still not granted, request them again
            repeatPermissionCheck(permissionList)
        } else {
            // All permissions have been granted or permanently denied
            Log.e(TAG, "onPermissionsResult ----> All permissions have been granted or permanently denied")
            Log.i(TAG, "permissionList ----> $permissionList")
            Log.i(TAG, "permanentlyDeniedPermissionList ----> $permanentlyDeniedPermissionList")
            // Proceed with your app logic here
            appLogic()
        }
    }

    private fun repeatPermissionCheck(requestPermissionList: List<String>) {
        val removePermissionList = ArrayList<String>()
        requestPermissionList.forEach { permission ->
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                removePermissionList.add(permission)
                Log.i(TAG, "Permission Granted: $permission")
            } else{
                Log.e(TAG, "Permission NOT Granted: $permission")
            }
        }
        if (removePermissionList.isNotEmpty()){
            removePermissionList.forEach {
                permissionList.remove(it)
            }
        }
        if (permissionList.isNotEmpty()){
            Log.d(TAG, "Some permissions are still not granted, request them again: $permissionList")
        }
        checkPermission(permissionList.toTypedArray())
    }

    private fun isAllPermissionGranted(): Boolean = permissionList.isEmpty() && permanentlyDeniedPermissionList.isEmpty()


    private fun alertDialogForPermanentlyDeniedPermissions(context: Context = this) {
        getFancyDialog().setTitle("Permission needed")
            .setCancelable(false)
            .setTopMessage("To use this service please grant below permission:\n")
            .setMiddleMessage(permanentlyDeniedPermissionList)
            .setBottomMessage("Please grant the permission from Settings.")
            .setBtn1("Settings") {
                Toast.makeText(context, "Navigating to settings", Toast.LENGTH_SHORT).show()
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
                getFancyDialog().dismiss()
            }.setBtn2("Cancel") {
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show()
                getFancyDialog().dismiss()
            }.setBtn3("Exit"){
                Toast.makeText(context, "Exit the app", Toast.LENGTH_SHORT).show()
                getFancyDialog().dismiss()
                finishAffinity()
            }.show()
    }

}







/*

//class RequestPermission(private val permissionsToRequest: Array<String> = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)): BaseActivity() {
//class RequestPermission(private val permissionsToRequest: Array<String>): BaseActivity() {
class RequestPermission(private val permissionsToRequest: Array<String>): BaseActivity() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Array<String>>
    private var permissionList = ArrayList<String>()
    private var permanentlyDeniedPermissionList = ArrayList<String>()
    private var grantedPermission = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewLayout(R.layout.activity_main)
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), ::onPermissionsResult)
        checkPermission()
    }

    private fun appLogic() {
        if (isAllPermissionGranted()) {
            // All permissions have been granted
        } else{
            // Not All permissions are granted
            alertDialogForPermanentlyDeniedPermissions()
        }
    }

    private fun checkPermission(permissions: Array<String>) {
        activityResultLauncher.launch(permissions)
    }

    private fun checkPermission() {
        permissionList.clear()
        permanentlyDeniedPermissionList.clear()
        grantedPermission = true
        permissionsToRequest.forEach { permission ->
            when {
                ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED -> {
                    Log.d(TAG, "$permission already granted")
                }
                ActivityCompat.shouldShowRequestPermissionRationale(this, permission) -> {
                    Log.d(TAG, "$permission has been previously denied, request it again")
                    permissionList.add(permission)
                    grantedPermission = false
                }
                permanentlyDeniedPermissionList.contains(permission) -> {
                    Log.e(TAG, "$permission has been permanently denied, do nothing")
                }
                else -> {
                    Log.d(TAG, "$permission not yet granted, add it to the permissionList")
                    permissionList.add(permission)
                    grantedPermission = false
                }
            }
        }

        if (!grantedPermission && !permanentlyDeniedPermissionList.containsAll(permissionList)) {
            Log.d(TAG, "Requesting permissions: $permissionList")
            checkPermission(permissionList.toTypedArray())
        } else {
            // All permissions are already granted or permanently denied
            Log.e(TAG, "checkPermission ----> All permissions are already granted or permanently denied")
            Log.i(TAG, "permissionList ----> $permissionList")
            Log.i(TAG, "permanentlyDeniedPermissionList ----> $permanentlyDeniedPermissionList")
            // Proceed with your app logic here
            appLogic()
        }

    }

    private fun onPermissionsResult(result: Map<String, Boolean>) {
        result.forEach { (permission, isGranted) ->
            if (!isGranted) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    Log.e(TAG, "$permission has been permanently denied, add it to the permanentlyDeniedPermissionList")
                    if (!permanentlyDeniedPermissionList.contains(permission)){
                        permanentlyDeniedPermissionList.add(permission)
                    }
                }
                grantedPermission = false
            }
        }
        if (!grantedPermission && !permanentlyDeniedPermissionList.containsAll(permissionList)) {
            // Some permissions are still not granted, request them again
            repeatPermissionCheck(permissionList)
        } else {
            // All permissions have been granted or permanently denied
            Log.e(TAG, "onPermissionsResult ----> All permissions have been granted or permanently denied")
            Log.i(TAG, "permissionList ----> $permissionList")
            Log.i(TAG, "permanentlyDeniedPermissionList ----> $permanentlyDeniedPermissionList")
            // Proceed with your app logic here
            appLogic()
        }
    }

    private fun repeatPermissionCheck(requestPermissionList: List<String>) {
        val removePermissionList = ArrayList<String>()
        requestPermissionList.forEach { permission ->
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                removePermissionList.add(permission)
                Log.i(TAG, "Permission Granted: $permission")
            } else{
                Log.e(TAG, "Permission NOT Granted: $permission")
            }
        }
        if (removePermissionList.isNotEmpty()){
            removePermissionList.forEach {
                permissionList.remove(it)
            }
        }
        if (permissionList.isNotEmpty()){
            Log.d(TAG, "Some permissions are still not granted, request them again: $permissionList")
        }
        checkPermission(permissionList.toTypedArray())
    }

    private fun isAllPermissionGranted(): Boolean = permissionList.isEmpty() && permanentlyDeniedPermissionList.isEmpty()


    private fun alertDialogForPermanentlyDeniedPermissions(context: Context = this) {
        getFancyDialog().setTitle("Permission needed")
            .setCancelable(false)
            .setTopMessage("To use this service please grant below permission:\n")
            .setMiddleMessage(permanentlyDeniedPermissionList)
            .setBottomMessage("Please grant the permission from Settings.")
            .setBtn1("Settings") {
                Toast.makeText(context, "Navigating to settings", Toast.LENGTH_SHORT).show()
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
                getFancyDialog().dismiss()
            }.setBtn2("Cancel") {
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show()
                getFancyDialog().dismiss()
            }.setBtn3("Exit"){
                Toast.makeText(context, "Exit the app", Toast.LENGTH_SHORT).show()
                getFancyDialog().dismiss()
                finishAffinity()
            }.show()
    }

}
 */