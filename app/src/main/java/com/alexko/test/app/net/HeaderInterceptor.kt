package com.alexko.test.app.net

import okhttp3.*

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .post(FormBody.Builder().add("appid", "c35880b49ff95391b3a6d0edd0c722eb").build())
                .build()
        )
    }
}