package com.eternalreturntoy.navigation

sealed class Screen (val route: String) {
    object Splash : Screen("splash")

}