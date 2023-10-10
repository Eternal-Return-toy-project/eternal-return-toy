package com.eternalreturntoy.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://open-api.bser.io"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: EternalReturnAPI = retrofit.create(EternalReturnAPI::class.java)
}