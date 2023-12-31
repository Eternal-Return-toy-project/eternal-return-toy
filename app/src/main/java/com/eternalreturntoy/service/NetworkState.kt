package com.eternalreturntoy.service

sealed class NetworkState{

    object None: NetworkState()

    object Connected: NetworkState()

    object NotConnected: NetworkState()
}
