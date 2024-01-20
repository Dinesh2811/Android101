package com.dinesh.android.app

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/*

private val networkMonitor: NetworkMonitor by lazy { NetworkMonitor }

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            networkMonitor.isConnected.collect { isConnected ->
                // Check for internet connectivity
                if (isConnected) {
                    // The device is connected to the internet
                    // You can perform your internet-dependent operations here.
                    Log.i(TAG, "onCreate: The device is connected to the internet")
                } else {
                    // The device is not connected to the internet
                    // Handle the absence of internet connectivity.
                    Log.e(TAG, "onCreate: The device is not connected to the internet")
                }
            }
        }
    }

}

override fun onStart() {
    super.onStart()
    networkMonitor.start(this)
}

override fun onStop() {
    super.onStop()
    networkMonitor.stop()
}

 */

interface ConnectionManager {
    fun isNetworkAvailable(): Boolean
    fun registerNetworkCallBack()
}
object NetworkMonitor {
    private const val TAG = "log_NetworkMonitor"
    private var connectivityManager: ConnectivityManager? = null
    private val _isConnected: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.d(TAG, "Network available")
            _isConnected.value = true
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            Log.d(TAG, "Losing: maxMsToLive = $maxMsToLive")
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Log.d(TAG, "Network lost")
            _isConnected.value = false
        }

        override fun onUnavailable() {
            super.onUnavailable()
            Log.d(TAG, "Unavailable: ")
            _isConnected.value = false
        }
    }

//    fun isNetworkAvailable(): Boolean {
//        val network = connectivityManager?.activeNetwork ?: return false
//        val networkCapabilities = connectivityManager?.getNetworkCapabilities(network) ?: return false
//        return when {
//            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
//            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
//            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_USB) -> true
//            else -> false
//        }
//    }

    fun getNetworkType(): NetworkType {
        val network = connectivityManager?.activeNetwork ?: return NetworkType.Unknown
        val networkCapabilities = connectivityManager?.getNetworkCapabilities(network) ?: return NetworkType.Unknown

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.Wifi
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkType.Cellular
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> NetworkType.Ethernet
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> NetworkType.Bluetooth
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_USB) -> NetworkType.Usb
            else -> NetworkType.Unknown
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

sealed class NetworkType {
    data object Wifi : NetworkType()
    data object Cellular : NetworkType()
    data object Ethernet : NetworkType()
    data object Bluetooth : NetworkType()
    data object Usb : NetworkType()
    data object Unknown : NetworkType()
}