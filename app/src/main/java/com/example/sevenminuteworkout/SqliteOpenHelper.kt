package com.example.sevenminuteworkout

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteOpenHelper(context: Context , factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context , DATABASE_NAME , factory, DATABASE_VERISON){

    companion object{

        private val DATABASE_VERISON = 1

        private val DATABASE_NAME = "Seven_Minute_Workout.db"

        private val DATABASE_TABLE = "history"

        private val ID_COLUMN = "id"

        private val COMPLETED_DATE = "date_completed"

    }
    override fun onCreate(db: SQLiteDatabase?) {

        val Create_Table = ("CREATE TABLE " +
                DATABASE_TABLE + "("
                + ID_COLUMN + " INTEGER PRIMARY KEY," +
                COMPLETED_DATE
                + " TEXT" + ")") // Create History Table Query.
        db?.execSQL(Create_Table)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE)
        onCreate(db)
    }

    fun addDates(date: String ){

        val values = ContentValues()
        values.put(COMPLETED_DATE,date)
        val db = this.writableDatabase

        db.insert(DATABASE_TABLE,null,values)
        db.close()

    }

    fun fetchDateValue() : ArrayList<String>{

        val list = ArrayList<String>()
        val db= this.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM $DATABASE_TABLE", null)

        while (cursor.moveToNext()){

          val getDate = (cursor.getString(cursor.getColumnIndex(COMPLETED_DATE)))
            list.add(getDate)

        }
        cursor.close()
        return list
    }



}