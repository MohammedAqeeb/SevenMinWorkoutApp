package com.example.sevenminuteworkout

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_finish.*
import kotlinx.android.synthetic.main.custom_dialog_button.*
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        setSupportActionBar(tbFinish)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        tbFinish.setNavigationOnClickListener {
            onBackPressed()
        }

        finishBtn.setOnClickListener {
            finish()
        }

        getDateForDB()
    }

    private fun getDateForDB(){

        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        Log.i("date:", ""+ dateTime)

        val sdf = SimpleDateFormat(" dd MMM yyyy hh:mm:ss ",Locale.getDefault())
        val date = sdf.format(dateTime)

        val dbHandler = SqliteOpenHelper(this, null)
        dbHandler.addDates(date)

        Log.i("date: ","Added")

    }
}


