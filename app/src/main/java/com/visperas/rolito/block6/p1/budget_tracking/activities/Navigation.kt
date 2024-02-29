package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.visperas.rolito.block6.p1.budget_tracking.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_navigation.bottom_navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.FrameLayout



class Navigation : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.menu.bottom_navigation_menu)

        bottom_navigation.setOnNavigationItemSelectedListener() { item ->
            when (item.itemId) {
                R.id.navigation_profile -> {
                    // Handle home navigation
                    // Example: startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.navigation_home -> {
                    // Handle dashboard navigation
                    // Example: startActivity(Intent(this, DashboardActivity::class.java))
                    true
                }
                R.id.navigation_settings -> {
                    // Handle notifications navigation
                    // Example: startActivity(Intent(this, NotificationsActivity::class.java))
                    true
                }
                else -> false
            }
        }

    }
}