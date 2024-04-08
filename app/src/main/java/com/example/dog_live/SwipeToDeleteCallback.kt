package com.example.dog_live

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.io.File

class SwipeToDeleteCallback(private val filesDir: File ,private val adapter: CardHodlerAdapter, private val dataList: MutableList<CardData>,private val recyclerView: RecyclerView) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val deletedItem = dataList[position]
        dataList.removeAt(position)// Сохраняем удаленный элемент
        adapter.deleteItem(position) // Удаляем элемент из адаптера

        // Удаление элемента из списка
        val controllerCardData = CardDataController()
        controllerCardData.removeOnRecord(filesDir,deletedItem)
        adapter.notifyItemRemoved(position)

        // Дополнительная логика после удаления элемента, например, отмена удаления
        Snackbar.make(recyclerView, "Элемент удален", Snackbar.LENGTH_LONG)
            .setAction("Отменить") {
                dataList.add(position, deletedItem) // Восстанавливаем удаленный элемент в список
                adapter.notifyItemInserted(position) // Уведомляем адаптер об изменениях
            }
            .show()
        }
}