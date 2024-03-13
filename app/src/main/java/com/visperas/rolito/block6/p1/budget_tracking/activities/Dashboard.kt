package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.visperas.rolito.block6.p1.budget_tracking.databinding.ActivityDashboardBinding

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardView1.setOnClickListener {
            startActivity(Intent(this@Dashboard, Expenses::class.java))
        }

        binding.cardView2.setOnClickListener {
            startActivity(Intent(this@Dashboard, Savings::class.java))
        }

    }
}