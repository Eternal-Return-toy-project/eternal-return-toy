package com.eternalreturntoy

import android.content.Context
import android.content.SharedPreferences
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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eternalreturntoy.data.remote.ApiClient
import com.eternalreturntoy.data.remote.RemoteDataSource
import com.eternalreturntoy.presentation.viewmodels.GameDataViewModel
import com.eternalreturntoy.ui.theme.EternalReturnToyTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 파일이 다운로드되지 않았다면 다운로드 합니다.
        if (!isL10nFileDownloaded()) {
            lifecycleScope.launch {
                downloadAndParseL10nFile()
            }
        }

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
    private fun isL10nFileDownloaded(): Boolean {
        return sharedPreferences.getBoolean("isL10nFileDownloaded", false)
    }

    private fun setL10nFileDownloaded() {
        sharedPreferences.edit().putBoolean("isL10nFileDownloaded", true).apply()
    }

    private suspend fun downloadAndParseL10nFile() {
        val api = ApiClient.api
        val remoteDataSource = RemoteDataSource(api, this)
        remoteDataSource.downloadAndParseL10nFile(RemoteDataSource.Language.Korean)
        setL10nFileDownloaded()
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                viewModel.fetchFreeCharacter("2")
                val response = viewModel.freeCharacterResponse.value
                message = response?.body()?.freeCharacters?.joinToString(", ") ?: viewModel.error.value.toString()
            }) {
                Text(text = "Normal")
            }

            Button(onClick = {
                viewModel.fetchFreeCharacter("3")
                val response = viewModel.freeCharacterResponse.value
                message = response?.body()?.freeCharacters?.joinToString(", ") ?: viewModel.error.value.toString()
            }) {
                Text(text = "Rank")
            }
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
