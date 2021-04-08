package com.example.sevenminuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutStart.setOnClickListener {
            val intent = Intent(this,WorkoutActivity::class.java)
            startActivity(intent)
        }
        llBMI.setOnClickListener {
            val intent2 = Intent(this, CalculateBMI::class.java)
            startActivity(intent2)
        }
        llHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}
