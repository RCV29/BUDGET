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

class Savings : AppCompatActivity() {

    private lateinit var adapter: SavingsAdapter
    private val dataList = mutableListOf<String>()

    private lateinit var recyclerView2: RecyclerView
    private lateinit var fabAdd2: FloatingActionButton

    companion object {
        private const val ADD_SAVINGS_REQUEST = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_savings)

        // Initialize views
        recyclerView2 = findViewById(R.id.recyclerView1)
        fabAdd2 = findViewById(R.id.fabAdd1)

        // Initialize RecyclerView adapter and layout manager
        adapter = SavingsAdapter(dataList)
        recyclerView2.adapter = adapter
        recyclerView2.layoutManager = LinearLayoutManager(this)

        fabAdd2.setOnClickListener {
            startActivityForResult(Intent(this@Savings, AddSavings::class.java), ADD_SAVINGS_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_SAVINGS_REQUEST && resultCode == Activity.RESULT_OK) {
            val description = data?.getStringExtra("description")
            val amount = data?.getStringExtra("amount")
            val date = data?.getStringExtra("date")

            // Create a CardView based on the received data
            val cardView = createCardView(description, amount, date)

            // Add the CardView to the RecyclerView
            dataList.add("$description - $amount - $date")
            adapter.notifyDataSetChanged()
        }
    }

    private fun createCardView(description: String?, amount: String?, date: String?): CardView {
        val cardView = CardView(this)
        // Customize the CardView as needed


        // Create TextViews to display the data
        val textViewDescription = TextView(this)
        textViewDescription.text = "Description: $description"

        val textViewAmount = TextView(this)
        textViewAmount.text = "Amount: $amount"

        val textViewDate = TextView(this)
        textViewDate.text = "Date: $date"

        // Add TextViews to the CardView
        cardView.addView(textViewDescription)
        cardView.addView(textViewAmount)
        cardView.addView(textViewDate)

        return cardView
    }
}
