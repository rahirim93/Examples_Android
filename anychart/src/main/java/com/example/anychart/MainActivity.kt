package com.example.anychart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonTooltip.setOnClickListener {
            startActivity(Intent(this, TooltipActivity::class.java))
        }
        startActivity(Intent(this, TooltipActivity::class.java))
    }
}