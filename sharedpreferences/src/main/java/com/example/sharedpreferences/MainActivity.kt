package com.example.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var editText: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonLoad: Button

    private var pref: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pref = getSharedPreferences("Name", Context.MODE_PRIVATE)

        textView = findViewById(R.id.textView)
        editText = findViewById(R.id.editText)
        buttonSave = findViewById(R.id.buttonSave)
        buttonLoad = findViewById(R.id.buttonLoad)

        //Подгрузка сохраненного значения при запуске
        var text = pref?.getInt("counter", 0).toString()
        textView.text = text

    }

    fun saveButton(view: android.view.View) {

    }
    // Подгрузка сохраненного значения
    fun loadButton(view: android.view.View) {
        var tx = pref?.getInt("counter", 0).toString()
        textView.text = tx
    }

    //Функция сохранения в sharedPreferences
    private fun saveData (saveData : Int) {
        val editor = pref?.edit()
        editor?.putInt("counter", saveData)
        editor?.apply()
    }

    /** При уничтожении activity
        сохранить значение editText
        в sharedPreferences */
    override fun onDestroy() {
        super.onDestroy()
        saveData(editText.text.toString().toInt())
    }


}