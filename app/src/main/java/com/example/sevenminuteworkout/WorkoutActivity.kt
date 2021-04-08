package com.example.sevenminuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.TestLooperManager
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_workout.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_dialog_button.*

class WorkoutActivity : AppCompatActivity(), TextToSpeech.OnInitListener {


    //RestView timer of 10 sec
    private var restTimer : CountDownTimer? = null
    private var restProgress = 0

    //ExerciseView of Timer for 30 sec
    private var exerciseTimer : CountDownTimer?=null
    private var exerciseProgress = 0
   // private var exerciseLongTimer : Long= 30

    //ExerciseModel name,Image,id
    private var exerciseList: ArrayList<ExerciseModel>?= null
    private var mCurrentExercisePosition= -1

    //TextToSpeech
    private var tts: TextToSpeech? = null

    //MediaPlayer
    private var mPlayer: MediaPlayer? = null

    //RecycleView of Exercise
    private var exerciseAdapter : ExerciseRecycleStatus?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        setSupportActionBar(tb_Activity)
        val actionBar = supportActionBar

        if(actionBar!=null){

            actionBar.setDisplayHomeAsUpEnabled(true)

        }
        tb_Activity.setNavigationOnClickListener {
            customDialogButton()
        }

        exerciseList = Constants.defaultExerciseList()

        setUpRestView()

        tts = TextToSpeech(this,this)

        setupExerciseStatusRC()
    }

    override fun onDestroy() {
        //once the application is closed timer, tts and media player STOPS
        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        if (tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(mPlayer!=null){
            mPlayer!!.stop()
        }
        super.onDestroy()
    }

        private fun setUpProgressTimer() {

            pbLayout.progress = restProgress
            restTimer = object : CountDownTimer(10000, 1000) {

                override fun onTick(millisUntilFinished: Long) {

                    restProgress++

                    pbLayout.progress = 10 - restProgress

                    tvText.text = (10 - restProgress).toString()
                }

                override fun onFinish() {

                    mCurrentExercisePosition++


                    exerciseList!![mCurrentExercisePosition].setisSelected(true)
                    exerciseAdapter!!.notifyDataSetChanged()

                    setExerciseView()

                }
            }.start()
        }
    private fun setUpRestView()
    {
        //media player in try and catch block to avoid it for crashing
        try {
            mPlayer= MediaPlayer.create(applicationContext,R.raw.press_start)
            mPlayer!!.isLooping
            mPlayer!!.start()
        } catch (e: Exception){
            e.printStackTrace()
        }

        llWorkout.visibility = View.VISIBLE

        llExercise.visibility = View.GONE

        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }

        tvNextExercise.text=exerciseList!![mCurrentExercisePosition + 1].getName()

        setUpProgressTimer()
    }


    private fun exerciseSetUpView(){

        pb_ExerciseLayout.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer( 30000,1000){
            override fun onTick(millisUntilFinished: Long) {

                exerciseProgress++

                pb_ExerciseLayout.progress = 30 - exerciseProgress

                tvExerciseText.text= (30 - exerciseProgress).toString()
            }

            override fun onFinish() {

                if (mCurrentExercisePosition < exerciseList?.size!! -1) {

                    exerciseList!![mCurrentExercisePosition].setisSelected(false)
                    exerciseList!![mCurrentExercisePosition].setisCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()

                    setUpRestView()


                } else{
                    finish()

                    val intent = Intent(this@WorkoutActivity,FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }
    private fun setExerciseView(){

        llWorkout.visibility = View.GONE

        llExercise.visibility = View.VISIBLE

        if(exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![mCurrentExercisePosition].getName())
        exerciseSetUpView()

        ivExerciseImage.setImageResource(exerciseList!![mCurrentExercisePosition].getImage())
        tvExerciseName.text= (exerciseList!![mCurrentExercisePosition].getName())

    }
        //TextToSpeech Functionality
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.UK)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","Text To Speech Language Not available")
            }
            else{
                Log.e("TTS","Initialization Failed")
            }
        }
    }
    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }


    //setUp recycleView for exercise

    private fun setupExerciseStatusRC(){

        rvExerciseStatus.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        exerciseAdapter = ExerciseRecycleStatus(exerciseList!!,this)

        rvExerciseStatus.adapter = exerciseAdapter

    }

    private fun customDialogButton() {

        val customDialog = Dialog(this)

        customDialog.setContentView(R.layout.custom_dialog_button)

        customDialog.cll_Yes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        customDialog.cll_No.setOnClickListener {

            customDialog.dismiss()
        }
        customDialog.show()
    }
}
