package com.example.dog_live

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.io.File
import java.lang.reflect.Type


class CardDataController{

    fun removeOnRecord(filesDir: File,data: CardData){
        val file = File(filesDir, "data.json")
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
        val file = File(filesDir, "data.json")
        val gson = Gson()

        if (file.readText().trim().isEmpty()) {
            val listData: MutableList<CardData> = mutableListOf()
            listData.add(data)
            val json = gson.toJson(listData)
            file.writeText(json)
            println("_________________________________________")
        } else {
            // Read existing JSON data
            val existingJson = file.readText()
            val listType: Type = object : TypeToken<List<CardData>>() {}.type
            val existingRecords: MutableList<CardData> = gson.fromJson(existingJson, listType)

            // Add new record to the list
            existingRecords.add(data)

            // Convert updated list to JSON
            val updatedJson = gson.toJson(existingRecords)

            // Write updated JSON data back to the file
            file.writeText(updatedJson)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun readAllRecord(filesDir: File): MutableList<CardData> {
        // Чтение данных из файла JSON

        val data: MutableList<CardData> = mutableListOf()
        val file = File(filesDir, "data.json")


        val json = file.readText()
        val gson = Gson()
        val listType: Type = object : TypeToken<List<CardData>>() {}.type
        // Вывод данных
        gson.fromJson<List<CardData>?>(json, listType)?.forEach {
            data.add(
                CardData(
                    petName = it.petName,
                    petBreed = it.petBreed,
                    timeRecord = it.timeRecord,
                    ownersName = it.ownersName,
                    dateRecord = it.dateRecord
                )
            )
        }
        return data


    }
}