package com.example.examenparcial_musicapp.model

import com.squareup.moshi.Json
import java.io.Serializable

data class Album(
    // la API puede devolver "id" o "_id" según el endpoint; guardamos ambos
    val id: String? = null,
    @Json(name = "_id")
    val _id: String? = null,
    val title: String,
    val artist: String,
    val description: String? = null,
    // en la API aparece "image"
    @Json(name = "image")
    val image: String? = null
) : Serializable {
    // helper para obtener un id único usable
    val albumId: String
        get() = id ?: _id ?: ""
}