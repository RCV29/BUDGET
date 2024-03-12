package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.visperas.rolito.block6.p1.budget_tracking.R

class ExpenseAdapter(private val dataList: MutableList<String>) :
    RecyclerView.Adapter<ExpenseAdapter.MyViewHolder>() {

    // ViewHolder class with views
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val textViewAmount: TextView = itemView.findViewById(R.id.textViewAmount)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
    }

    // onCreateViewHolder to inflate the layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.expense_item, parent, false)
        return MyViewHolder(view)
    }

    // onBindViewHolder to bind data to views
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val expense = dataList[position].split(" - ")

        holder.textViewDescription.text = "Description: ${expense[0]}"
        holder.textViewAmount.text = "Amount: ${expense[1]}"
        holder.textViewDate.text = "Date: ${expense[2]}"

        // Set click listener for the card view
        holder.cardView.setOnClickListener {
            // Handle click for update operation
            val context = holder.itemView.context
            val intent = Intent(context, UpdateExpenseActivity::class.java)
            intent.putExtra("description", expense[0])
            intent.putExtra("amount", expense[1])
            intent.putExtra("date", expense[2])
            context.startActivity(intent)
        }

        // Set long click listener for the card view

    }

    // getItemCount to return the size of the data list
    override fun getItemCount(): Int {
        return dataList.size
    }
}

