package com.example.garage.WebSocket

import com.example.garage.CardData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkManager(private val retrofitClient: RetrofitClient) {

    fun fetchCards(onSuccess: (List<CardData>) -> Unit, onFailure: (Throwable) -> Unit) {
        retrofitClient.apiService.fetchCards().enqueue(object : Callback<List<CardData>> {
            override fun onResponse(call: Call<List<CardData>>, response: Response<List<CardData>>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    println("1_______1")
                    onFailure(Throwable("Ошибка при получении данных"))
                }
            }

            override fun onFailure(call: Call<List<CardData>>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun addCard(cardData: CardData, onSuccess: () -> Unit, onFailure: (Throwable) -> Unit) {
        retrofitClient.apiService.addCard(cardData).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    // Логирование для отладки
                    println("Ошибка при добавлении данных: ${response.errorBody()?.string() ?: "Неизвестная ошибка"}")
                    onFailure(Throwable("Ошибка при добавлении данных: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Логирование для отладки
                println("Ошибка сети: ${t.message}")
                onFailure(t)
            }
        })
    }
}