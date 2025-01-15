package com.example.garage.WebSocket

import com.example.garage.CardData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("/getCards")
    fun fetchCards(): Call<List<CardData>>

    @POST("/addCard")
    fun addCard(@Body cardData: CardData): Call<Void>
}