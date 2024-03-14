package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.visperas.rolito.block6.p1.budget_tracking.R
import com.visperas.rolito.block6.p1.budget_tracking.models.Expense

class ExpenseAdapter(private val dataList: MutableList<Expense>) :
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
        val expense = dataList[position]

        holder.textViewDescription.text = "Description: ${expense.exp}"
        holder.textViewAmount.text = "Amount: ${expense.price}"
        holder.textViewDate.text = "Date: ${expense.date}"

        // Set click listener for the card view
        holder.cardView.setOnClickListener {
            // Handle click for update operation
            val context = holder.itemView.context
            val intent = Intent(context, UpdateExpenseActivity::class.java)
            intent.putExtra("expenseId", expense.id) // Pass the expense id to UpdateExpenseActivity
            intent.putExtra("description", expense.exp)
            intent.putExtra("user_id", expense.user_id)
            intent.putExtra("amount", expense.price)
            intent.putExtra("date", expense.date)
            context.startActivity(intent)
        }

        // Set long click listener for the card view
        holder.cardView.setOnLongClickListener {
            // Handle long click if needed
            true
        }
    }

    // getItemCount to return the size of the data list
    override fun getItemCount(): Int {
        return dataList.size
    }
}