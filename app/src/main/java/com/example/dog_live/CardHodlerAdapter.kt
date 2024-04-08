package com.example.dog_live

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView



class CardHodlerAdapter(private val data: MutableList<CardData>) : RecyclerView.Adapter<ViewRecordHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewRecordHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item_recording_layout,parent, false)
        return ViewRecordHolder(view)

    }

    override fun onBindViewHolder(holder: ViewRecordHolder, position: Int) {

         val data = data.get(position)
         holder.ownersNameTextView.text = data.ownersName
         holder.petBreedTextView.text = data.petBreed
         holder.petNameTextView.text = data.petName
         holder.timeRecordTextView.text = data.timeRecord

    }

    fun deleteItem(position: Int) {

        // Удалите элемент из вашего списка данных
        if (position == 0) {
            data.clear()
        } else {
            data.removeAt(position)
            notifyItemRemoved(position)
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }

}


