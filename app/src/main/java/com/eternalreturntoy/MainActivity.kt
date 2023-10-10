package com.eternalreturntoy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eternalreturntoy.presentation.GameDataViewModel
import com.eternalreturntoy.ui.theme.EternalReturnToyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EternalReturnToyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: GameDataViewModel = viewModel()) {
    var message by remember { mutableStateOf("Press the button to fetch data") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            viewModel.fetchDataByMetaType("hash")
            val response = viewModel.gameDataResponse.value
            message = response?.body()?.toString() ?: viewModel.error.value.toString()
        }) {
            Text(text = "Fetch Game Data")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = message)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    EternalReturnToyTheme {
        MainScreen()
    }
}
