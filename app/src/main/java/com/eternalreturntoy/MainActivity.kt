package com.eternalreturntoy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.navigation.NavHostController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.eternalreturntoy.ui.theme.EternalReturnToyTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.eternalreturntoy.navigation.SetupNavGraph
import com.eternalreturntoy.service.NetworkState
import com.eternalreturntoy.ui.theme.PrimaryColor

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EternalReturnToyTheme {

                val viewModel: MainViewModel = hiltViewModel()
                val networkState by viewModel.networkState.collectAsState(initial = NetworkState.None)

                LaunchedEffect(networkState) {
                }

                when (networkState) {
                    NetworkState.None -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(color = PrimaryColor)
                        }
                    }

                    NetworkState.Connected -> {
                        navController = rememberNavController()
                        SetupNavGraph(navController = navController)

                    }

                    NetworkState.NotConnected -> {
                    }
                }
            }
        }
    }
}
