package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.visperas.rolito.block6.p1.budget_tracking.R
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

        // Menu icon click listener
        val menuIcon = findViewById<ImageView>(R.id.menu_icon)

        menuIcon.setOnClickListener {
            val popupMenu = PopupMenu(this@Dashboard, it)
            popupMenu.menuInflater.inflate(R.menu.menu_main, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.menu_logout) {
                    startActivity(Intent(this@Dashboard, MainActivity::class.java))
                    Toast.makeText(this@Dashboard, "Logged out", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                false
            }
            popupMenu.show()
        }
    }
}
