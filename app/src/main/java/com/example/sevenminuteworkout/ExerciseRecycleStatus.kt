package com.example.sevenminuteworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_exercise_status.view.*


class ExerciseRecycleStatus(val items:ArrayList<ExerciseModel>,val context: Context): RecyclerView.Adapter<ExerciseRecycleStatus.ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model:ExerciseModel= items[position]

        holder.tvItem.text = model.getID().toString()

        if(model.getisSelected()){
            holder.tvItem.background= ContextCompat.getDrawable(context,R.drawable.item_color_exercise_status)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        }
        else if(model.getisCompleted()){
            holder.tvItem.background = ContextCompat.getDrawable(context,R.drawable.item_completed_exercise_status)
            holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
        }else{

            holder.tvItem.background = ContextCompat.getDrawable(context,R.drawable.tvitem_gray_circular_bg)
            holder.tvItem.setTextColor(Color.parseColor("#FF0000"))

        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_exercise_status,parent,false))
    }


    class ViewHolder(view : View): RecyclerView.ViewHolder(view){

        val tvItem = view.tvItems

    }

}