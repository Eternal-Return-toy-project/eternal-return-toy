package com.eternalreturntoy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun SetupNavGraph(navController: NavHostController) {

    NavHost(navController = navController,
        startDestination = Screen.Splash.route) {

        composable(route = Screen.Splash.route) {
            // TODO 스플래시 스크린 추가
        }
    }
}