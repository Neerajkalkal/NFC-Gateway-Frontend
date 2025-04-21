package com.example.nfc_gatway.Network.Retrofit

import com.example.nfc_gatway.Network.ApiService.ApiService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.getValue

object RetrofitInstance {
    private val retrofit by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS) // ⏱ Increase connection timeout
            .readTimeout(60, TimeUnit.SECONDS)    // ⏱ Increase read timeout
            .writeTimeout(60, TimeUnit.SECONDS)   // ⏱ Increase write timeout
            .addInterceptor(logging)
            .build()


            val gson = GsonBuilder()
                .setLenient() // Enable lenient parsing
                .create()

        Retrofit.Builder()
            .baseUrl("http://52.66.19.77:8081/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}


