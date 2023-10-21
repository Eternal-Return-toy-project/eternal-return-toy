package com.eternalreturntoy.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eternalreturntoy.data.remote.ApiClient
import com.eternalreturntoy.data.remote.EternalReturnAPI
import com.eternalreturntoy.data.remote.models.DataResponse
import com.eternalreturntoy.data.remote.models.FreeCharacters
import kotlinx.coroutines.launch
import retrofit2.Response

class GameDataViewModel(private val api: EternalReturnAPI = ApiClient.api) : ViewModel() {

    val gameDataResponse: MutableLiveData<Response<DataResponse>> = MutableLiveData()
    val freeCharacterResponse: MutableLiveData<Response<FreeCharacters>> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    fun fetchDataByMetaType(metaType: String) {
        viewModelScope.launch {
            try {
                val response = api.fetchDataByMetaType(metaType)
                if (response.isSuccessful) {
                    gameDataResponse.postValue(response)
                    Log.d("GameDataViewModel", "API Response Body: ${response.body()}")
                } else {
                    error.postValue("Error: ${response.code()}")
                    Log.d("GameDataViewModel", "API Error Response: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                error.postValue(e.message ?: "An error occurred")
                Log.e("GameDataViewModel", "Exception: $e")
            }
        }
    }

    fun fetchFreeCharacter(matchingMode: String) {
        viewModelScope.launch {
            try {
                val response = api.fetchFreeCharacter(matchingMode)
                if (response.isSuccessful) {
                    freeCharacterResponse.postValue(response)
                    Log.d("GameDataViewModel", "API Response Body: ${response.body()}")
                } else {
                    error.postValue("Error: ${response.code()}")
                    Log.d("GameDataViewModel", "API Error Response: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                error.postValue(e.message ?: "An error occurred")
                Log.e("GameDataViewModel", "Exception: $e")
            }
        }
    }
}