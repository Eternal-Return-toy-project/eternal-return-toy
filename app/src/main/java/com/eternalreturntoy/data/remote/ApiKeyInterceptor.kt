package com.eternalreturntoy.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import com.eternalreturntoy.BuildConfig

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("x-api-key", BuildConfig.ETERNAL_RETURN_API_KEY)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}