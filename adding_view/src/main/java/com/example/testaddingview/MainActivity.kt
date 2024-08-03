package com.example.testaddingview

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)
        //val button = layoutInflater.inflate(R.layout.button_mine, null, false)
//        gridLayout.addView(layoutInflater.inflate(R.layout.button_mine, null, false))
//        gridLayout.addView(layoutInflater.inflate(R.layout.button_mine, null, false))
//        gridLayout.addView(layoutInflater.inflate(R.layout.button_mine, null, false))
//        gridLayout.addView(layoutInflater.inflate(R.layout.button_mine, null, false))



        var tree = gridLayout.childCount

        button = findViewById(R.id.button)
        button.setOnClickListener {
//            Toast.makeText(this, "${gridLayout.childCount}", Toast.LENGTH_SHORT).show()
//            gridLayout.addView(layoutInflater.inflate(R.layout.button_mine, null, false))
//            var params =  gridLayout.getChildAt(0).layoutParams
//            //params.width = 300
//            GridLayout.s
            val param = GridLayout.LayoutParams(GridLayout.spec(
                GridLayout.UNDEFINED,GridLayout.FILL,1f),
                GridLayout.spec(GridLayout.UNDEFINED,GridLayout.FILL,1f))
            var button1 = Button(this)
            button1.layoutParams = param
//            button1.layoutParams = GridLayout.LayoutParams(GridLayout.spec(0, 1F), GridLayout.spec(0, 1F))
            gridLayout.addView(button1)
//            button1.layoutParams = GridLayout.LayoutParams(GridLayout.spec(1, 1F), GridLayout.spec(1, 1F))
//            gridLayout.addView(button1)



        }

    }
}