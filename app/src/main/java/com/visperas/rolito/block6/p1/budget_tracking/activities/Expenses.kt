package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.visperas.rolito.block6.p1.budget_tracking.R

class Expenses : AppCompatActivity() {

    private lateinit var adapter: ExpenseAdapter
    private val dataList = mutableListOf<String>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAdd: FloatingActionButton

    companion object {
        private const val ADD_EXPENSE_REQUEST = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses)

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView)
        fabAdd = findViewById(R.id.fabAdd)

        // Initialize RecyclerView adapter and layout manager
        adapter = ExpenseAdapter(dataList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        fabAdd.setOnClickListener {
            startActivityForResult(Intent(this@Expenses, AddExpenses::class.java), ADD_EXPENSE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_EXPENSE_REQUEST && resultCode == Activity.RESULT_OK) {
            val description = data?.getStringExtra("description")
            val amount = data?.getStringExtra("amount")
            val date = data?.getStringExtra("date")
            val category = data?.getStringExtra("category")

            // Create a CardView based on the received data
            val cardView = createCardView(description, amount, date, category)

            // Add the CardView to the RecyclerView
            dataList.add("$description - $amount - $date - $category")
            adapter.notifyDataSetChanged()
        }
    }

    private fun createCardView(description: String?, amount: String?, date: String?, category: String?): CardView {
        val cardView = CardView(this)
        // Customize the CardView as needed

        // Create TextViews to display the data
        val textViewDescription = TextView(this)
        textViewDescription.text = "Description: $description"

        val textViewAmount = TextView(this)
        textViewAmount.text = "Amount: $amount"

        val textViewDate = TextView(this)
        textViewDate.text = "Date: $date"

        val textViewCategory = TextView(this)
        textViewCategory.text = "Category: $category"

        // Add TextViews to the CardView
        cardView.addView(textViewDescription)
        cardView.addView(textViewAmount)
        cardView.addView(textViewDate)
        cardView.addView(textViewCategory)

        return cardView
    }
}
