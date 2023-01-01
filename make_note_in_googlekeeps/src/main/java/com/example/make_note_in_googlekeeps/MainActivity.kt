package com.example.make_note_in_googlekeeps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val keepIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                setPackage("com.google.android.keep")
                putExtra(Intent.EXTRA_SUBJECT, "Shopping List " + "recipe.getName()")   // Задаем текст заголовка
                putExtra(Intent.EXTRA_TEXT, "Flower\nyeast\nbutter\nalmonds")           // Задаем тексть заметки
            }
            startActivity(keepIntent)
        }
    }
}