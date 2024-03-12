package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.visperas.rolito.block6.p1.budget_tracking.R

class SavingsAdapter(private val dataList: MutableList<String>) :
    RecyclerView.Adapter<SavingsAdapter.MyViewHolder>() {

    // ViewHolder class with views
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView2: CardView = itemView.findViewById(R.id.cardView1)
        val textViewAmount2: TextView = itemView.findViewById(R.id.textViewAmount1)
        val textViewDate2: TextView = itemView.findViewById(R.id.textViewDate1)
    }

    // onCreateViewHolder to inflate the layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.savings_item, parent, false)
        return MyViewHolder(view)
    }

    // onBindViewHolder to bind data to views
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val savings = dataList[position].split(" - ")

        holder.textViewAmount2.text = "Amount: ${savings[0]}"
        holder.textViewDate2.text = "Date: ${savings[1]}"

        // Set click listener for the card view
        holder.cardView2.setOnClickListener {
            // Handle click for update operation
            val context = holder.itemView.context
            val intent = Intent(context, UpdateExpenseActivity::class.java)
            intent.putExtra("description", savings[0])
            intent.putExtra("amount", savings[1])
            intent.putExtra("date", savings[2])
            context.startActivity(intent)
        }

        // Set long click listener for the card view
        holder.cardView2.setOnLongClickListener {
            // Handle long click for delete operation
            dataList.removeAt(position)
            notifyItemRemoved(position)
            true
        }
    }

    // getItemCount to return the size of the data list
    override fun getItemCount(): Int {
        return dataList.size
    }
}

