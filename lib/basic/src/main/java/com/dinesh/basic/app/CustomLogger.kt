package com.dinesh.basic.app

import android.util.Log
import com.dinesh.basic.BuildConfig


sealed class LogLevel {
    data object Debug : LogLevel()
    data object Info : LogLevel()
    data object Warning : LogLevel()
    data object Error : LogLevel()
}

class CustomLogger private constructor() {

    companion object {
        @Volatile
        private var instance: CustomLogger? = null

        fun getInstance(): CustomLogger {
            return instance ?: synchronized(this) {
                instance ?: CustomLogger().also { instance = it }
            }
        }
    }

    fun log(logLevel: LogLevel, TAG: String, message: String) {
        if (BuildConfig.DEBUG) {
            showLog(logLevel, TAG, message)
        } else {

        }
    }

    private fun showLog(logLevel: LogLevel, TAG: String, message: String) {
        when (logLevel) {
            LogLevel.Debug -> Log.d(TAG, message)
            LogLevel.Info -> Log.i(TAG, message)
            LogLevel.Warning -> Log.w(TAG, message)
            LogLevel.Error -> Log.e(TAG, message)
        }
    }
}

fun LogLevel.log(message: String, TAG: String = "log_") {
    CustomLogger.getInstance().log(this, TAG, message)
}