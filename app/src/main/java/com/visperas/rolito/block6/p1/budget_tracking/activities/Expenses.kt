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
}
