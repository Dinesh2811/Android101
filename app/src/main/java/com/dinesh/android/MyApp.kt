package com.dinesh.android

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.pluto.Pluto
import com.pluto.plugins.exceptions.PlutoExceptionsPlugin
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        Pluto.Installer(this)
            .addPlugin(PlutoExceptionsPlugin())
            .install()

        registerActivityLifecycleCallbacks(object: ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                activity.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.white)
            }

            override fun onActivityStarted(activity: Activity) {
                // Override onActivityStarted if needed
            }

            override fun onActivityResumed(activity: Activity) {
                // Override onActivityResumed if needed
            }

            override fun onActivityPaused(activity: Activity) {
                // Override onActivityPaused if needed
            }

            override fun onActivityStopped(activity: Activity) {
                // Override onActivityStopped if needed
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                // Override onActivitySaveInstanceState if needed
            }

            override fun onActivityDestroyed(activity: Activity) {
                // Override onActivityDestroyed if needed
            }
        })
    }
}