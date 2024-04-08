package com.example.dog_live

import android.annotation.SuppressLint
import android.app.Activity

import android.app.DatePickerDialog

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Button
import android.widget.DatePicker

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

import java.text.SimpleDateFormat

import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val file = File(filesDir,"data.json")
        if (!file.exists())
            file.createNewFile()

        val userPhotoView: ImageView = findViewById(R.id.imageView_userPhoto)
        userPhotoView.setImageResource(R.drawable.img)

        val userNameText: TextView = findViewById(R.id.textView_UserName)
        userNameText.setText("Новый пользователь")
        val userPostText: TextView = findViewById(R.id.textView_userPost)
        userPostText.setText("Админ")

        val dateTextView: TextView = findViewById(R.id.textView_date)

        val currentDate = SimpleDateFormat("d.M.yyyy", Locale.getDefault()).format(Date())
        dateTextView.setText("Сегодня $currentDate")

        val controllerCardData = CardDataController()

        val cardHolderRecyclerView: RecyclerView = findViewById(R.id.recycleViewCardHolder)
        val filesDir: File = this.filesDir

        val cardDataList: MutableList<CardData>? = controllerCardData.readAllRecord(filesDir)


        fillRecycleView(cardDataList,currentDate,cardHolderRecyclerView)

        updateRecyclerView(cardDataList,currentDate)

        val buttonDatePicker: Button = findViewById(R.id.button_calendar)
        buttonDatePicker.setOnClickListener{
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    // Обработка выбранной даты
                    val selectedDate = "$dayOfMonth.${monthOfYear + 1}.$year"
                    if (selectedDate == currentDate.toString())
                        dateTextView.setText("Сегодня $selectedDate")
                    else
                        dateTextView.setText(selectedDate)
                    updateRecyclerView(cardDataList,selectedDate)
                }, year, month, day)
            datePickerDialog.show()

        }
        if (cardDataList != null) {
            removeRecyclerView(cardDataList,cardHolderRecyclerView)
        }

        val fab: FloatingActionButton = findViewById(R.id.floatingActionButton_create_a_new_record)
        fab.setOnClickListener{
            val intentRecordActivity = Intent(this, RecordActivity::class.java)
            startActivityForResult(intentRecordActivity, SECOND_ACTIVITY_REQUEST_CODE)
            //Дальнейшая обработка введенных данных
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fillRecycleView(cardDataList: MutableList<CardData>?,currentDate: String,cardHolderRecyclerView: RecyclerView){

        cardHolderRecyclerView.layoutManager = LinearLayoutManager(this)

        val cardDataFilterable = mutableListOf<CardData>()

        if (cardDataList?.isNotEmpty() == true) {
            for (data in cardDataList) {
                if (data.dateRecord?.format(DateTimeFormatter.ofPattern("d.M.yyyy"))
                        .toString() == currentDate
                ) {
                    cardDataFilterable.add(data)
                }
            }
        }
        val adapter = CardHodlerAdapter(cardDataFilterable)
        cardHolderRecyclerView.adapter = adapter
    }

    private  fun removeRecyclerView(cardDataList: MutableList<CardData>, cardHolderRecyclerView: RecyclerView){

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(filesDir,CardHodlerAdapter(cardDataList),cardDataList,cardHolderRecyclerView))
        itemTouchHelper.attachToRecyclerView(cardHolderRecyclerView)

    }

    val SECOND_ACTIVITY_REQUEST_CODE = 1


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            recreate()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateRecyclerView(cardDataList: MutableList<CardData>?, selectedDate: String) {
        val cardHolderRecyclerView: RecyclerView = findViewById(R.id.recycleViewCardHolder)
        cardHolderRecyclerView.layoutManager = LinearLayoutManager(this)

        val emptyTextView: TextView = findViewById(R.id.textView_empty)

        val cardDataFilterable = mutableListOf<CardData>()


        cardDataList?.forEach{
            if (it.dateRecord == selectedDate){
                cardDataFilterable.add(it)
            }
        }

        val adapter = CardHodlerAdapter(cardDataFilterable)
        cardHolderRecyclerView.adapter = adapter

        if (cardDataFilterable.isEmpty()) {
            cardHolderRecyclerView.visibility = View.GONE
            emptyTextView.visibility = View.VISIBLE
        } else {
            cardHolderRecyclerView.visibility = View.VISIBLE
            emptyTextView.visibility = View.GONE
        }

    }





}
