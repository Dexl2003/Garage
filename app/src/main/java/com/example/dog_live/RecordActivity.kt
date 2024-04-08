package com.example.dog_live

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.File



class RecordActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)


        val ownerName: EditText? = findViewById(R.id.editText_ownersName)
        val petName: EditText? = findViewById(R.id.editText_petName)
        val petBreed: EditText? = findViewById(R.id.editText_petBreed)
        val timeRecord: EditText? = findViewById(R.id.editText_timePicker)
        val dateRecord: EditText? = findViewById(R.id.editText_datePicker)


        dateRecord?.setOnClickListener{
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                val selectedDate = "$dayOfMonth.${monthOfYear + 1}.$year"
                dateRecord.setText(selectedDate)
            }, year, month, day)

            datePickerDialog.show()

        }

        timeRecord?.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                // Обработка выбранного времени
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                timeRecord.setText(selectedTime)
            }, hour, minute, true)

            timePickerDialog.show()
        }

        val cancelButton: Button = findViewById(R.id.cancel_button)
        val recordButton: Button = findViewById(R.id.record_button)

        cancelButton.setOnClickListener{
            finish()
        }

        recordButton.setOnClickListener{

            val item = CardData(
                petName =    petName?.text.toString(),
                petBreed =   petBreed?.text.toString(),
                timeRecord = timeRecord?.text.toString(),
                ownersName = ownerName?.text.toString(),
                dateRecord = dateRecord?.text.toString()
            )
            val controllerCardData = CardDataController()
            val filesDir: File = this.filesDir
            controllerCardData.createANewRecord(filesDir,item)

            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }



    }
}