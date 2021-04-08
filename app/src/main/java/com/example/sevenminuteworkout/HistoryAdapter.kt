package com.example.sevenminuteworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history_row.view.*

class HistoryAdapter (val context: Context ,val itemList: ArrayList<String>):
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val llHistoryView = view.ll_History_main!!
        val tvPositionView = view.tvPosition!!
        val tvDateView = view.tvDate!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_history_row,parent,false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val date: String = itemList.get(position)

        holder.tvPositionView.text = (position+1).toString()

        holder.tvDateView.text =date

        if(position % 2 == 0){
            holder.llHistoryView.setBackgroundColor(Color.parseColor("#A9F5F2"))
        }else{
            holder.llHistoryView.setBackgroundColor(Color.parseColor("#E6E6E6"))
        }

    }
}