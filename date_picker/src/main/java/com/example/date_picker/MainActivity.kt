package com.example.date_picker

import android.app.DatePickerDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var buttonDate: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonDate = findViewById(R.id.button_date)
        buttonDate.setOnClickListener {
            showDialog(1)
        }
        textView = findViewById(R.id.textView)
        textView.gravity = 17
    }

    protected override fun onCreateDialog(id: Int): Dialog {
        if (id == 1) {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            var datePickerDialog = DatePickerDialog(this, myCallBack, year, month, day)
            return datePickerDialog
        }
        return super.onCreateDialog(id)
    }
    var myCallBack = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        Toast.makeText(this, "Year: $year. Month: $month. Day: $dayOfMonth" , Toast.LENGTH_SHORT).show()
        textView.text = "Выбранная дата: \n Год: $year. Месяц: $month. День: $dayOfMonth."
    }
}

