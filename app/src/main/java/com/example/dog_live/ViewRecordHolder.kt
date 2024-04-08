package com.example.dog_live

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


class ViewRecordHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val petNameTextView: TextView = itemView.findViewById(R.id.textView_petName)
    val petBreedTextView: TextView = itemView.findViewById(R.id.textView_PetBreed)
    val timeRecordTextView: TextView = itemView.findViewById(R.id.textView_timeRecord)
    val ownersNameTextView: TextView = itemView.findViewById(R.id.textView_OwnersName)

}
