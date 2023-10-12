package com.eternalreturntoy.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import com.eternalreturntoy.BuildConfig
import com.eternalreturntoy.data.CryptoUtils

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val stardustDragon = CryptoUtils.decrypt(BuildConfig.ShootingSonic)
        val requestBuilder = original.newBuilder()
            .header("x-api-key", stardustDragon)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}