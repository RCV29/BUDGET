package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.visperas.rolito.block6.p1.budget_tracking.R
import com.visperas.rolito.block6.p1.budget_tracking.api.RetrofitClient
import com.visperas.rolito.block6.p1.budget_tracking.models.DefaultResponse
import com.visperas.rolito.block6.p1.budget_tracking.models.Expense
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class AddExpenses : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expenses)

        val descriptionEdit = findViewById<EditText>(R.id.editDescription)
        val amountEdit = findViewById<EditText>(R.id.editAmount)
        val dateEdit = findViewById<EditText>(R.id.editDate)
        val addButton = findViewById<Button>(R.id.addButton)
        val toolBar = findViewById<ImageView>(R.id.left_icon)

        toolBar.setOnClickListener {
            onBackPressed()
        }

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
            val exp = descriptionEdit.text.toString()
            val price = amountEdit.text.toString()
            val date = dateEdit.text.toString()

            // Create Expense object
            val expense = Expense(
                id = 0, // Assuming you're assigning IDs on the server side
                user_id = 0, // Assuming you're assigning user IDs on the server side
                exp = exp,
                price = price,
                date = date,
                created_at = "", // Empty string, as it's managed by the server
                updated_at = ""  // Empty string, as it's managed by the server
            )

            // Make a network request to create the expense
            createExpense(expense)
        }
    }

    private fun createExpense(expense: Expense) {
        // Make a network request using Retrofit
        val api = RetrofitClient.instance

        val token = sharedPreferences.getString("token", "")

        if (token.isNullOrEmpty()) {
            // Handle case where token is not available
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
            return
        }

        api.postExpense("Bearer $token", expense).enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                if (response.isSuccessful) {
                    // Expense created successfully
                    Toast.makeText(this@AddExpenses, "Expense created successfully", Toast.LENGTH_SHORT).show()

                    // Finish the activity
                    finish()
                } else {
                    Toast.makeText(this@AddExpenses, "Failed to create expense", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Toast.makeText(this@AddExpenses, "Error creating expense: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

