package com.appiadev.ituneschallenge.data.api

import com.appiadev.ituneschallenge.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private const val BASE_URL = "https://itunes.apple.com/"


    fun getRetrofit(): Retrofit {
        val clientBuilder = OkHttpClient.Builder()
            .readTimeout(300, TimeUnit.MINUTES)
            .writeTimeout(400, TimeUnit.MINUTES)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(loggingInterceptor)
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            //.addCallAdapterFactory(.create())
            .client(clientBuilder.build())
            .build()
    }
}