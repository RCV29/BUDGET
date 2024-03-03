package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.visperas.rolito.block6.p1.budget_tracking.R

class AddExpenses : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expenses)

        val descriptionEditText = findViewById<EditText>(R.id.editDescription)
        val amountEditText = findViewById<EditText>(R.id.editAmount)
        val dateEditText = findViewById<EditText>(R.id.editDate)
        val categorySpinner = findViewById<Spinner>(R.id.categorySpinner)
        val addButton = findViewById<Button>(R.id.addButton)

        // Populate the spinner with categories
        val categories = arrayOf(
            "Shopping", "Groceries", "Skincare", "Food",
            "Transportation", "Bills", "Subscription", "Insurance"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        addButton.setOnClickListener {
            val description = descriptionEditText.text.toString()
            val amount = amountEditText.text.toString()
            val date = dateEditText.text.toString()
            val category = categorySpinner.selectedItem.toString()

            val intent = Intent()
            intent.putExtra("description", description)
            intent.putExtra("amount", amount)
            intent.putExtra("date", date)
            intent.putExtra("category", category)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}

