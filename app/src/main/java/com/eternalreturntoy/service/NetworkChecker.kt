package com.eternalreturntoy.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NetworkChecker @Inject constructor(
    @ApplicationContext
    private val appContext: Context
) {
    private val _networkState = MutableStateFlow<NetworkState>(NetworkState.None)
    val networkState: StateFlow<NetworkState> = _networkState

    private val validTransportTypes = listOf(
        NetworkCapabilities.TRANSPORT_WIFI,
        NetworkCapabilities.TRANSPORT_CELLULAR
    )

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _networkState.value = NetworkState.Connected
            Log.d("NetworkChecker", "Network is available: ${_networkState.value}")
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            _networkState.value = NetworkState.NotConnected
            Log.d("NetworkChecker", "Network is lost: ${_networkState.value}")
        }
    }

    private val connectivityManager: ConnectivityManager? =
        appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    init {
        connectivityManager?.run {
            initiateNetworkState(this)
            registerNetworkCallback(this)
        }
    }

    private fun initiateNetworkState(manager: ConnectivityManager) {
        _networkState.value = manager.activeNetwork?.let {
            manager.getNetworkCapabilities(it)
        }?.let { networkCapabilities ->
            if (validTransportTypes.any { networkCapabilities.hasTransport(it) }) {
                Log.d("NetworkChecker", "Initial network state: Connected")
                NetworkState.Connected
            } else {
                Log.d("NetworkChecker", "Initial network state: Not Connected")
                NetworkState.NotConnected
            }
        } ?: NetworkState.NotConnected
    }

    private fun registerNetworkCallback(manager: ConnectivityManager) {
        NetworkRequest.Builder().apply {
            validTransportTypes.onEach { addTransportType(it) }
        }.let {
            manager.registerNetworkCallback(it.build(), networkCallback)
        }
    }
}