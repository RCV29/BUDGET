package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.visperas.rolito.block6.p1.budget_tracking.R
import com.visperas.rolito.block6.p1.budget_tracking.api.RetrofitClient
import com.visperas.rolito.block6.p1.budget_tracking.models.DefaultResponse
import com.visperas.rolito.block6.p1.budget_tracking.models.Saving
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class AddSavings : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_savings)

        val amountEdit = findViewById<EditText>(R.id.editAmount)
        val dateEdit = findViewById<EditText>(R.id.editDate)
        val addButton = findViewById<Button>(R.id.addButton)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        dateEdit.setOnClickListener {
            // Date picker dialog
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDayOfMonth"
                    dateEdit.setText(selectedDate)
                },
                year,
                month,
                dayOfMonth
            )

            datePickerDialog.show()
        }

        addButton.setOnClickListener {
            // Retrieve input data
            val amount = amountEdit.text.toString()
            val date = dateEdit.text.toString()

            // Create saving object
            val saving = Saving(
                id = 0, // Assuming you're assigning IDs on the server side
                user_id = 0, // Assuming you're assigning user IDs on the server side
                amount = amount,
                date = date,
                created_at = "", // Empty string, as it's managed by the server
                updated_at = ""  // Empty string, as it's managed by the server
            )

            // Make a network request to create the saving
            createSaving(saving)
        }
    }

    private fun createSaving(saving: Saving) {
        // Make a network request using Retrofit
        val api = RetrofitClient.instance

        val token = sharedPreferences.getString("token", "")

        if (token.isNullOrEmpty()) {
            // Handle case where token is not available
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
            return
        }

        api.postSaving("Bearer $token", saving).enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                if (response.isSuccessful) {
                    // Saving created successfully
                    Toast.makeText(this@AddSavings, "Saving created successfully", Toast.LENGTH_SHORT).show()

                    // Finish the activity
                    finish()
                } else {
                    Toast.makeText(this@AddSavings, "Failed to create Saving", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Toast.makeText(this@AddSavings, "Error creating saving: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

