package com.visperas.rolito.block6.p1.budget_tracking.activities


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.visperas.rolito.block6.p1.budget_tracking.R
import com.visperas.rolito.block6.p1.budget_tracking.api.RetrofitClient
import com.visperas.rolito.block6.p1.budget_tracking.models.Expense
import com.visperas.rolito.block6.p1.budget_tracking.models.ExpenseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Expenses : AppCompatActivity() {

    private lateinit var adapter: ExpenseAdapter
    private val dataList = mutableListOf<Expense>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAdd: FloatingActionButton

    private lateinit var sharedPreferences: SharedPreferences

    private val refreshHandler = Handler()
    private val refreshInterval = 1000L

    private val refreshRunnable = object : Runnable {
        override fun run() {
            loadExpenses()
            refreshHandler.postDelayed(this, refreshInterval)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses)

        val toolBar = findViewById<ImageView>(R.id.left_icon)
        val menuIcon = findViewById<ImageView>(R.id.menu_icon)

        menuIcon.setOnClickListener {
            val popupMenu = PopupMenu(this@Expenses, it)
            popupMenu.menuInflater.inflate(R.menu.menu_main, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.menu_logout) {
                    startActivity(Intent(this@Expenses, MainActivity::class.java))
                    Toast.makeText(this@Expenses, "Logged out", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                false
            }
            popupMenu.show()
        }

        toolBar.setOnClickListener {
            onBackPressed()
        }

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView)
        fabAdd = findViewById(R.id.fabAdd)

        // Initialize RecyclerView adapter and layout manager
        adapter = ExpenseAdapter(this, dataList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Load expenses from the API
        refreshHandler.postDelayed(refreshRunnable, refreshInterval)
        loadExpenses()

        fabAdd.setOnClickListener {
            startActivity(Intent(this@Expenses, AddExpenses::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop auto-refresh when activity is destroyed
        refreshHandler.removeCallbacks(refreshRunnable)
    }

    private fun loadExpenses() {
        // Make a network request using Retrofit
        val api = RetrofitClient.instance

        val token = sharedPreferences.getString("token", "")

        if (token.isNullOrEmpty()) {
            // Handle case where token is not available
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
            return
        }

        api.getExpense("Bearer $token").enqueue(object : Callback<ExpenseResponse> {
            override fun onResponse(call: Call<ExpenseResponse>, response: Response<ExpenseResponse>) {
                if (response.isSuccessful) {
                    // Update dataList with the fetched expenses
                    val expenseResponse = response.body()
                    expenseResponse?.let {
                        dataList.clear()
                        dataList.addAll(it.expenses) // Add all Expense objects to dataList

                        // Notify the adapter that data has changed
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@Expenses, "Failed to fetch expenses", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ExpenseResponse>, t: Throwable) {
                Toast.makeText(this@Expenses, "Error loading expenses: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}