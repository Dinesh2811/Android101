package com.dinesh.basic.app.network.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.util.Log
import com.dinesh.basic.app.LogLevel
import com.dinesh.basic.app.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object NetworkMonitor {
    private const val TAG = "log_NetworkMonitor"
    private var connectivityManager: ConnectivityManager? = null
    private val _isConnected: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val networkCallback = object: ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            LogLevel.Debug.log(TAG = TAG, message = "onAvailable: ${network}")
            _isConnected.value = true
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            Log.d(TAG, "Losing: maxMsToLive = $maxMsToLive")
            LogLevel.Error.log(TAG = TAG, message = "onLosing: maxMsToLive = $maxMsToLive")
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            LogLevel.Error.log(TAG = TAG, message = "onLost: ${network}")
            _isConnected.value = false
        }

        override fun onUnavailable() {
            super.onUnavailable()
            LogLevel.Error.log(TAG = TAG, message = "onUnavailable: Network Unavailable")
            _isConnected.value = false
        }
    }

    fun start(context: Context) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder().build()
        connectivityManager?.registerNetworkCallback(networkRequest, networkCallback)
    }

    fun stop() {
        connectivityManager?.unregisterNetworkCallback(networkCallback)
    }
}