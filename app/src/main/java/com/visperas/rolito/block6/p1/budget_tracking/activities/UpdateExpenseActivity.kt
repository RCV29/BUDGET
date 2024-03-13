package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.visperas.rolito.block6.p1.budget_tracking.R

class UpdateExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_expense)

        // Retrieve data from intent and populate UI fields for update
        val description = intent.getStringExtra("description")
        val amount = intent.getStringExtra("amount")
        val date = intent.getStringExtra("date")

        // Populate UI fields with retrieved data
        // Implement update functionality here
    }
}
