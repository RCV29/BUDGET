package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.visperas.rolito.block6.p1.budget_tracking.R
import androidx.cardview.widget.CardView
import android.widget.Toast

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val expenses = findViewById<CardView>(R.id.cardView1)
        val savings = findViewById<CardView>(R.id.cardView2)

        expenses.setOnClickListener {
            val intent = Intent(this@Dashboard, Expenses::class.java)
            startActivity(intent)
        }

        savings.setOnClickListener {
            val intent = Intent(this@Dashboard, Savings::class.java)
            startActivity(intent)
        }


    }
}