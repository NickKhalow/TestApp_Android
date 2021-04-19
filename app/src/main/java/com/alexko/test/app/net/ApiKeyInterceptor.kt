package com.alexko.test.app.net

import okhttp3.*
import java.io.IOException


class ApiKeyInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val original = chain.request()
        val originalHttpUrl = original.url()
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("appid", "c35880b49ff95391b3a6d0edd0c722eb")
            .build()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
            .url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}