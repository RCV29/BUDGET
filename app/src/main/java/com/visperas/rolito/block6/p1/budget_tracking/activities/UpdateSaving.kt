package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.visperas.rolito.block6.p1.budget_tracking.R
import com.visperas.rolito.block6.p1.budget_tracking.api.Api
import com.visperas.rolito.block6.p1.budget_tracking.api.RetrofitClient
import com.visperas.rolito.block6.p1.budget_tracking.models.DefaultResponse
import com.visperas.rolito.block6.p1.budget_tracking.models.Saving
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateSaving : AppCompatActivity() {

    private lateinit var api: Api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_saving)
        val toolBar = findViewById<ImageView>(R.id.left_icon)
        val menuIcon = findViewById<ImageView>(R.id.menu_icon)

        menuIcon.setOnClickListener {
            val popupMenu = PopupMenu(this@UpdateSaving, it)
            popupMenu.menuInflater.inflate(R.menu.menu_main, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.menu_logout) {
                    startActivity(Intent(this@UpdateSaving, MainActivity::class.java))
                    Toast.makeText(this@UpdateSaving, "Logged out", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                false
            }
            popupMenu.show()
        }

        toolBar.setOnClickListener {
            onBackPressed()
        }

        // Initialize Retrofit
        api = RetrofitClient.instance

        // Retrieve data from intent and populate UI fields for update
        val savingId = intent.getIntExtra("savingId", 0) // Default value 0
        val amount = intent.getStringExtra("amount")
        val date = intent.getStringExtra("date")
        val user_id = intent.getIntExtra("user_id", 0)

        // Populate UI fields with retrieved data
        val amountEdit = findViewById<EditText>(R.id.editTextAmount)
        val dateEdit = findViewById<EditText>(R.id.editTextDate)

        amountEdit.setText(amount)
        dateEdit.setText(date)

        // Implement update functionality
        val buttonSave = findViewById<CardView>(R.id.buttonSave)
        buttonSave.setOnClickListener {
            val updatedAmount = amountEdit.text.toString()
            val updatedDate = dateEdit.text.toString()

            // Create an updated Expense object
            val updatedSaving = Saving(
                id = savingId, // Use expenseId from intent
                user_id = user_id, // Assuming you're assigning user IDs on the server side
                amount = updatedAmount,
                date = updatedDate,
                created_at = "", // Empty string, as it's managed by the server
                updated_at = "" // Empty string, as it's managed by the server
            )

            // Call the function to update the expense
            updateSaving(updatedSaving)
        }
    }

    private fun updateSaving(updatedSaving: Saving) {
        // Make a network request using Retrofit
        val token = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token", "")

        if (token.isNullOrEmpty()) {
            // Handle case where token is not available
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
            return
        }

        // Call the update expense API
        api.updateSaving("Bearer $token", updatedSaving.id, updatedSaving)
            .enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    if (response.isSuccessful) {
                        // Handle successful update
                        Toast.makeText(
                            this@UpdateSaving,
                            "Expense updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Finish the activity
                        finish()
                    } else {
                        // Handle unsuccessful update
                        val message = response.errorBody()?.string()
                        Toast.makeText(
                            this@UpdateSaving,
                            "Failed to update expense: $message",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    // Handle failure
                    Toast.makeText(
                        this@UpdateSaving,
                        "Error updating expense: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}