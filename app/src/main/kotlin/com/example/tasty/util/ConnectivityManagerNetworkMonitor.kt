package com.example.tasty.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import com.example.tasty.data.network.NetworkMonitor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import javax.inject.Inject

class ConnectivityManagerNetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkMonitor {
    override val isOnline: Flow<Boolean> = callbackFlow {
        val connectivityManager = context.getSystemService<ConnectivityManager>()
        if (connectivityManager == null) {
            channel.trySend(false)
            channel.close()
            return@callbackFlow
        }
        val callback = getNetworkCallback(channel)
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, callback)
        sendConnectivityStatusToChannel(channel, connectivityManager.isCurrentlyConnected())
        awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
    }.conflate()

    private fun ConnectivityManager.isCurrentlyConnected() = activeNetwork
        ?.let(::getNetworkCapabilities)
        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false

    /**
     * Sends the latest connectivity status to the underlying channel.
     */
    private fun sendConnectivityStatusToChannel(
        channel: SendChannel<Boolean>,
        isCurrentlyConnected: Boolean
    ) {
        channel.trySend(isCurrentlyConnected)
    }

    /**
     * The callback's methods are invoked on changes to *any* network matching the [NetworkRequest],
     * not just the active network. So we can simply track the presence (or absence) of such [Network].
     */
    private fun getNetworkCallback(channel: SendChannel<Boolean>) = object : ConnectivityManager.NetworkCallback() {

        private val networks = mutableSetOf<Network>()

        override fun onAvailable(network: Network) {
            networks += network
            channel.trySend(true)
        }

        override fun onLost(network: Network) {
            networks -= network
            channel.trySend(networks.isNotEmpty())
        }
    }
}
