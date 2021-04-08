package com.example.sevenminuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_workout.*

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(tb_History)

        val actionBar = supportActionBar
        if(actionBar!=null){
           actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title =  "History"
        }
        tb_History.setNavigationOnClickListener {
            onBackPressed()
        }
        displayDate()

    }

    private fun displayDate(){

        val dbHandler = SqliteOpenHelper(this,null)

        val getAllDateValue = dbHandler.fetchDateValue()

        if(getAllDateValue.size >0){

            tvHistory.visibility = View.VISIBLE
            rvHistory.visibility = View.VISIBLE
            tvUnavailable.visibility = View.GONE

            rvHistory.layoutManager = LinearLayoutManager(this)

           val historyAdapterOBJ = HistoryAdapter(this,getAllDateValue)

            rvHistory.adapter = historyAdapterOBJ
        }
        else{

            tvHistory.visibility = View.GONE
            rvHistory.visibility = View.GONE
            tvUnavailable.visibility = View.VISIBLE
        }
    }
}
