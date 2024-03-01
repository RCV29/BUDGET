package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.visperas.rolito.block6.p1.budget_tracking.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_dashboard.bottom_navigation

class Dashboard : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_expenses -> {
                    startActivity(Intent(this, Expenses::class.java))
                    true
                }

                R.id.navigation_dashboard -> {
                    startActivity(Intent(this, Dashboard::class.java))
                    true
                }

                R.id.navigation_savings -> {
                    startActivity(Intent(this, Saving::class.java))
                    true
                }

                else -> false
            }
        }

    }


}