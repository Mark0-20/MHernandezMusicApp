package com.example.examenparcial_musicapp.data

import com.example.examenparcial_musicapp.model.Album
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


object RetrofitClient {
    private const val BASE_URL = "https://music.juanfrausto.com/"

    private val moshi = Moshi.Builder()
        .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory()) // <<-- importante
        .build()

    val api: MusicApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(MusicApi::class.java)
    }
}