package com.example.sevenminuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calculate_bmi.*
import java.math.BigDecimal
import java.math.RoundingMode

class CalculateBMI : AppCompatActivity() {

    val METRIC_UNIT_VIEW = "METRIC_UNIT_VIEW"
    val US_UNIT_VIEW = "US_UNIT_VIEW"

    var mCurrentView : String = METRIC_UNIT_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_bmi)

        setSupportActionBar(tb_BMI)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "Calculate BMI"
        }

        tb_BMI.setNavigationOnClickListener {
            onBackPressed()
        }

        //For radio button select for metric value
        btnCalculateUnits.setOnClickListener {

            if(mCurrentView.equals(METRIC_UNIT_VIEW)) {

                if (validateMetricValue()) {

                    val weightValue: Float = etMetricUnitWeight.text.toString().toFloat()

                    val heightValue: Float = etMetricUnitHeight.text.toString().toFloat()

                    val bmi = weightValue / (heightValue * heightValue)

                        displayBMIResult(bmi)
                        } else {
                        Toast.makeText(this, "Please Enter Valid Inputs", Toast.LENGTH_SHORT).show()
                        }
            } //For radio button select for US value
            else {
                if(validateUsValue()){

                    val usFeetValue: Float = etUsUnitHeightFeed.text.toString().toFloat()

                    val usInchValue: Float = etUsUnitHeightInch.text.toString().toFloat()

                    val usWeightValue: Float = etUsUnitWeight.text.toString().toFloat()

                    val usHeightValue =   usInchValue + usFeetValue * 12

                    val bmi = 703 * (usWeightValue / (usHeightValue * usHeightValue))

                    displayBMIResult(bmi)
                }
                else {
                    Toast.makeText(this, "Please Enter Valid Inputs", Toast.LENGTH_SHORT).show()
                }
            }
        }

        visibleMetricFunctionality()

        rgBtn.setOnCheckedChangeListener { group: RadioGroup, checkedId:Int ->
            if (checkedId == R.id.rbMetricUnit){
                visibleMetricFunctionality()
            }
            else {
                visibleUsFunctionality()
            }
        }
    }

    private fun visibleMetricFunctionality(){

        mCurrentView = METRIC_UNIT_VIEW

        tilMetricUnitHeight.visibility = View.VISIBLE
        tilMetricUnitWeight.visibility = View.VISIBLE

        etUsUnitHeightFeed.text!!.clear()
        etUsUnitHeightInch.text!!.clear()
        etUsUnitWeight.text!!.clear()

        tilUsUnitWeight.visibility = View.GONE
        llUsUnitHeight.visibility = View.GONE

        llDiplayBMIResult.visibility = View.INVISIBLE

    }

    private fun visibleUsFunctionality(){

        mCurrentView = US_UNIT_VIEW

        tilUsUnitWeight.visibility = View.VISIBLE
        llUsUnitHeight.visibility = View.VISIBLE

        etMetricUnitHeight.text!!.clear()
        etMetricUnitWeight.text!!.clear()

        tilMetricUnitHeight.visibility = View.GONE
        tilMetricUnitWeight.visibility = View.GONE

        llDiplayBMIResult.visibility = View.INVISIBLE

    }



    private fun validateMetricValue(): Boolean{
        var isValid = true

        if(etMetricUnitWeight.text.toString().isEmpty())
            isValid = false

        else if(etMetricUnitHeight.text.toString().isEmpty())
            isValid = false

        return isValid
    }


    private fun validateUsValue(): Boolean{
        var isValid = true

        if(etUsUnitWeight.text.toString().isEmpty())
            isValid = false

        else if(etUsUnitHeightFeed.text.toString().isEmpty())
            isValid = false

        else if(etUsUnitHeightInch.text.toString().isEmpty())
            isValid = false

        return isValid
    }

    private fun displayBMIResult(bmi: Float){
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        llDiplayBMIResult.visibility = View.VISIBLE

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        tvBMIValue.text = bmiValue
        tvBMIType.text = bmiLabel
        tvBMIDescription.text = bmiDescription
    }
}
