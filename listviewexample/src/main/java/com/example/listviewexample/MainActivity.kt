package com.example.listviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)

        //Массив для заполнения listView
        var itemList = ArrayList<String>()
        itemList.add("Егор")
        itemList.add("Семен")
        itemList.add("Елена")
        itemList.add("Дмитрий")
        itemList.add("Татьяна")
        itemList.add("Сергей")
        itemList.add("Станислав")

        //Адаптер как промежуточное звено между массивом элементов и самим listView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList)
        listView.adapter = adapter

        //Подключения слушателя нажатий на элементах listView
        listView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this,
                "Нажата позиция: $position с именем ${itemList[position]}",
                Toast.LENGTH_SHORT).show()
        }
    }
}