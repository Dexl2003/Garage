package com.example.garage

import android.annotation.SuppressLint
import com.example.garage.WebSocket.NetworkManager
import com.example.garage.WebSocket.RetrofitClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.io.File
import java.lang.reflect.Type


class CardDataController{

    private val retrofitClient = RetrofitClient()
    private val networkManager = NetworkManager(retrofitClient)
    fun removeOnRecord(filesDir: File,data: CardData){
        val file = File(filesDir, "date.json")
        val gson = Gson()

        if (file.readText().trim().isNotEmpty()) {
            // Read existing JSON data
            val existingJson = file.readText()
            val listType: Type = object : TypeToken<List<CardData>>() {}.type
            val existingRecords: MutableList<CardData> = gson.fromJson(existingJson, listType)

            // Add new record to the list
            existingRecords.remove(data)

            // Convert updated list to JSON
            val updatedJson = gson.toJson(existingRecords)

            // Write updated JSON data back to the file
            file.writeText(updatedJson)
        }
    }

    fun createANewRecord(filesDir: File,data: CardData) {
        println("________!_____")
        val file = File(filesDir, "date.json")
        val gson = Gson()
        var listData: MutableList<CardData> = mutableListOf()
        addCardIfNotExists(data, onSuccess = {}, onFailure = {})


    }

    @SuppressLint("SuspiciousIndentation")
    fun readAllRecord(filesDir: File): MutableList<CardData> {
        // Чтение данных из файла JSON


        val data: MutableList<CardData> = mutableListOf()
        val file = File(filesDir, "date.json")
//        println(file.toString())
        val json = file.readText()
        val gson = Gson()
        val listType: Type = object : TypeToken<List<CardData>>() {}.type
//        // Вывод данных
        gson.fromJson<List<CardData>?>(json, listType)?.forEach {
            data.add(
                CardData(
                    carName = it.carName,
                    carType = it.carType,
                    timeRecord = it.timeRecord,
                    ownersName = it.ownersName,
                    dateRecord = it.dateRecord
                )
            )
        }

        return data
    }

    fun addCardIfNotExists(cardData: CardData, onSuccess: () -> Unit, onFailure: (String) -> Unit) {


        // Код для добавления карточки через Retrofit
        networkManager.addCard(cardData,
            onSuccess = {
                println("Карточка успешно добавлена")
            },
            onFailure = { error ->
                println("Ошибка: ${error.message}")
            }
        )


    }

    fun fetchCardsFromDatabase(
        onSuccess: (List<CardData>) -> Unit,
        onFailure: (String) -> Unit
    )  {
        // Используем networkManager для получения данных с сервера
        networkManager.fetchCards(
            onSuccess = { cards ->
                if (cards.isNotEmpty()) {
                    onSuccess(cards) // Если данные успешно получены, вызываем onSuccess

                } else {
                    onFailure("Нет данных в базе") // Если данных нет, вызываем onFailure
                }
            },
            onFailure = { error ->println("_____")
                onFailure("Ошибка при получении данных: ${error.message}") // Обработка ошибки
            }
        )
    }
}