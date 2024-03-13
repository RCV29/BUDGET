package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.visperas.rolito.block6.p1.budget_tracking.api.RetrofitClient
import com.visperas.rolito.block6.p1.budget_tracking.databinding.ActivitySavingsBinding
import com.visperas.rolito.block6.p1.budget_tracking.models.Saving
import com.visperas.rolito.block6.p1.budget_tracking.models.SavingResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Savings : AppCompatActivity() {

    private lateinit var adapter: SavingsAdapter
    private val dataList = mutableListOf<Saving>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAdd: FloatingActionButton

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySavingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize views
        recyclerView = binding.recyclerView
        fabAdd = binding.fabAdd

        // Initialize RecyclerView adapter and layout manager
        adapter = SavingsAdapter(dataList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Load savings from the API
        loadSavings()

        fabAdd.setOnClickListener {
            startActivity(Intent(this@Savings, AddSavings::class.java))
        }
    }

    private fun loadSavings() {
        // Make a network request using Retrofit
        val api = RetrofitClient.instance

        val token = sharedPreferences.getString("token", "")

        if (token.isNullOrEmpty()) {
            // Handle case where token is not available
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_SHORT).show()
            return
        }

        api.getSaving("Bearer $token").enqueue(object : Callback<SavingResponse> {
            override fun onResponse(call: Call<SavingResponse>, response: Response<SavingResponse>) {
                if (response.isSuccessful) {
                    // Update dataList with the fetched savings
                    val savingResponse = response.body()
                    savingResponse?.let {
                        dataList.clear()
                        dataList.addAll(it.savings) // Add all saving objects to dataList

                        // Notify the adapter that data has changed
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@Savings, "Failed to fetch saving", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SavingResponse>, t: Throwable) {
                Toast.makeText(this@Savings, "Error loading saving: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}



