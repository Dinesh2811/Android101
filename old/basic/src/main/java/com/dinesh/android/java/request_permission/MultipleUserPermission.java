package com.dinesh.android.java.request_permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class MultipleUserPermission extends AppCompatActivity {
    private final String TAG = "log_" + getClass().getName().split(getClass().getName().split("\\.")[2] + ".")[1];

    ActivityResultLauncher<String[]> activityResultLauncher;
    ArrayList<String> permissionList = new ArrayList<>();
    ArrayList<String> permanentlyDeniedPermissionList = new ArrayList<>();
    boolean isAllPermissionGranted = true;

    @Override
    protected void onStart() {
        super.onStart();

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                Log.i(TAG, "onActivityResult: ----> " + result);

                if (!result.isEmpty()) {
                    Collection<Boolean> booleanCollection = result.values();

                    for (Boolean booleanResult : booleanCollection) {
                        isAllPermissionGranted = booleanResult && isAllPermissionGranted;
                    }

                    if (!isAllPermissionGranted) {
                        requestPermissionRational(MultipleUserPermission.this);
                    }
                }
            }
        });

        requestPermission(MultipleUserPermission.this);

    }

    public void requestPermission(Context context) {
        permissionList.clear();

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Permission Granted
            Log.i(TAG, "requestPermission: ----> " + Manifest.permission.ACCESS_FINE_LOCATION + " Granted");
        } else {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            //Permission Granted
            Log.i(TAG, "requestPermission: ----> " + Manifest.permission.CAMERA + " Granted");
        } else {
            permissionList.add(Manifest.permission.CAMERA);
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //Permission Granted
            Log.i(TAG, "requestPermission: ----> " + Manifest.permission.READ_EXTERNAL_STORAGE + " Granted");
        } else {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (!permissionList.isEmpty()) {
            Log.i(TAG, "requestPermissionList: " + permissionList);
            activityResultLauncher.launch(permissionList.toArray(new String[0]));
        }
    }

    public void requestPermissionRational(Context context) {
        permissionList.clear();
        permanentlyDeniedPermissionList.clear();

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "requestPermissionRational--> Permission Granted: " + (Manifest.permission.ACCESS_FINE_LOCATION));
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, (Manifest.permission.ACCESS_FINE_LOCATION))) {
                //This block here means PERMANENTLY DENIED PERMISSION
                Log.i(TAG, "requestPermissionRational: AlertDialog ----> PERMANENTLY DENIED PERMISSION: " + (Manifest.permission.ACCESS_FINE_LOCATION));

                permanentlyDeniedPermissionList.add("LOCATION");
            } else {
                Log.e(TAG, "requestPermissionRational--> Permission Denied & Requesting Permission Again: " + (Manifest.permission.ACCESS_FINE_LOCATION));
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "requestPermissionRational--> Permission Granted: " + (Manifest.permission.CAMERA));
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, (Manifest.permission.CAMERA))) {
                //This block here means PERMANENTLY DENIED PERMISSION
                Log.i(TAG, "requestPermissionRational: AlertDialog ----> PERMANENTLY DENIED PERMISSION: " + (Manifest.permission.CAMERA));

                permanentlyDeniedPermissionList.add("CAMERA");
            } else {
                Log.e(TAG, "requestPermissionRational--> Permission Denied & Requesting Permission Again: " + (Manifest.permission.CAMERA));
                permissionList.add(Manifest.permission.CAMERA);
            }
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "requestPermissionRational--> Permission Granted: " + (Manifest.permission.READ_EXTERNAL_STORAGE));
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, (Manifest.permission.READ_EXTERNAL_STORAGE))) {
                //This block here means PERMANENTLY DENIED PERMISSION
                Log.i(TAG, "requestPermissionRational: AlertDialog ----> PERMANENTLY DENIED PERMISSION: " + (Manifest.permission.READ_EXTERNAL_STORAGE));

                permanentlyDeniedPermissionList.add("STORAGE");
            } else {
                Log.e(TAG, "requestPermissionRational--> Permission Denied & Requesting Permission Again: " + (Manifest.permission.READ_EXTERNAL_STORAGE));
                permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }

        if (!permissionList.isEmpty()) {
            activityResultLauncher.launch(permissionList.toArray(new String[0]));
        }

        if (!permanentlyDeniedPermissionList.isEmpty()) {
            alertDialogForPermanentlyDeniedPermissions(MultipleUserPermission.this);
            Log.e(TAG, "permanentlyDeniedPermissionList: " + permanentlyDeniedPermissionList);
        }


    }

    public void alertDialogForPermanentlyDeniedPermissions(Context context) {
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle("Request Permission")
                .setMessage("You have permanently denied " + permanentlyDeniedPermissionList + " permission.\n\n" +
                        "To use the app please allow above permission " +
                        "\n\n\n" +
                        "Do you want to go to settings to allow permission?")
                .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Exit the app", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                        finishAffinity();
                        finishAndRemoveTask();
                    }
                })
                .show();

    }



    /*


        @Override
        protected void onStart() {
            super.onStart();

            requestingLocationPermission();
        }

        public void requestingLocationPermission() {
            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Log.e(TAG, "Location : OFF");

                AlertDialog builder = new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setTitle("Location")
                        .setMessage("Location is off. Do you want to turn on?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ClassName.this, "Please turn on the location(GPS)", Toast.LENGTH_SHORT).show();
    //                            dialog.dismiss();
                            }
                        }).show();
            } else {
                Log.i(TAG, "Location : ON");
            }

            if (ContextCompat.checkSelfPermission(ClassName.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Permission Granted
                Log.i(TAG, "requestingPermission: ----> " + Manifest.permission.ACCESS_FINE_LOCATION + " Granted");
            } else {
                //Permission not Granted
                //Request for permission
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, REQUEST_FINE_LOCATION);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            }
        }


        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "onRequestPermissionsResult--> Permission Granted: " + permissions[0]);
            } else {
                Log.e(TAG, "onRequestPermissionsResult--> Permission Denied: " + permissions[0]);
                if (!ActivityCompat.shouldShowRequestPermissionRationale(ClassName.this, permissions[0])) {
                    //This block here means PERMANENTLY DENIED PERMISSION
                    Log.i(TAG, "onRequestPermissionsResult: AlertDialog");
                    new AlertDialog.Builder(ClassName.this)
                            .setCancelable(false)
                            .setTitle("Request Location Permission")
                            .setMessage("You have permanently denied location permission. Please allow permission to access location to use this app. \n\n\n" +
                                    "Do you want to go to settings to allow permission?")
                            .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(ClassName.this, "Exit the app", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    finish();
                                    finishAffinity();
                                    finishAndRemoveTask();
                                }
                            })
                            .show();
                } else {
                    Log.i(TAG, "onRequestPermissionsResult--> Permission Denied Again " + permissions[0]);
                    ActivityCompat.requestPermissions(this, new String[]{permissions[0]}, REQUEST_FINE_LOCATION);
                }
            }
        }






     */

}