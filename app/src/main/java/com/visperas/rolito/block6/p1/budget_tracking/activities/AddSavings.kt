package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.visperas.rolito.block6.p1.budget_tracking.R

class AddSavings : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_savings)

        val descriptionEdit = findViewById<EditText>(R.id.editDescription1)
        val amountEdit = findViewById<EditText>(R.id.editAmount1)
        val dateEdit = findViewById<EditText>(R.id.editDate1)
        val addButton = findViewById<Button>(R.id.addButton1)


        addButton.setOnClickListener {
            val description = descriptionEdit.text.toString()
            val amount = amountEdit.text.toString()
            val date = dateEdit.text.toString()

            val intent = Intent()
            intent.putExtra("description", description)
            intent.putExtra("amount", amount)
            intent.putExtra("date", date)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}

