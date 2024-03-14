package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.visperas.rolito.block6.p1.budget_tracking.R
import com.visperas.rolito.block6.p1.budget_tracking.api.RetrofitClient
import com.visperas.rolito.block6.p1.budget_tracking.models.DefaultResponse
import com.visperas.rolito.block6.p1.budget_tracking.models.Saving
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SavingsAdapter(private val context: Context, private val dataList: MutableList<Saving>) :
    RecyclerView.Adapter<SavingsAdapter.MyViewHolder>() {

    // ViewHolder class with views
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val textViewAmount: TextView = itemView.findViewById(R.id.textViewAmount)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
    }

    // onCreateViewHolder to inflate the layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.savings_item, parent, false)
        return MyViewHolder(view)
    }

    // onBindViewHolder to bind data to views
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val saving = dataList[position]

        holder.textViewAmount.text = "Amount: ${saving.amount}"
        holder.textViewDate.text = "Date: ${saving.date}"

        // Set click listener for the card view
        holder.cardView.setOnClickListener {
            // Handle click for update operation
            val intent = Intent(context, UpdateSaving::class.java)
            intent.putExtra("savingId", saving.id) // Pass the expense id to UpdateExpenseActivity
            intent.putExtra("amount", saving.amount)
            intent.putExtra("user_id", saving.user_id)
            intent.putExtra("date", saving.date)
            context.startActivity(intent)
        }

        // Set long click listener for the card view
        holder.cardView.setOnLongClickListener {
            // Handle long click for delete operation
            showDeleteConfirmationDialog(saving.id)
            true
        }
    }

    // getItemCount to return the size of the data list
    override fun getItemCount(): Int {
        return dataList.size
    }

    private fun showDeleteConfirmationDialog(savingId: Int) {
        AlertDialog.Builder(context)
            .setTitle("Confirm Delete")
            .setMessage("Are you sure you want to delete this saving?")
            .setPositiveButton("Delete") { dialog, _ ->
                deleteSaving(savingId)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun deleteSaving(savingId: Int) {
        // Call your delete API here
        val token = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token", "")

        if (token.isNullOrEmpty()) {
            // Handle case where token is not available
            Toast.makeText(context, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
            return
        }

        // Assuming you have an API instance defined somewhere
        val api = RetrofitClient.instance

        // Make the API call to delete the saving
        api.deleteSaving("Bearer $token", savingId).enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                if (response.isSuccessful) {
                    // Handle successful deletion
                    val position = dataList.indexOfFirst { it.id == savingId }
                    if (position != -1) {
                        dataList.removeAt(position)
                        notifyItemRemoved(position)
                        Toast.makeText(context, "Saving deleted successfully", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Handle unsuccessful deletion
                    Toast.makeText(context, "Failed to delete saving", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                // Handle failure
                Toast.makeText(context, "Error deleting saving: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}