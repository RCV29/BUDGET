package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.visperas.rolito.block6.p1.budget_tracking.R
import java.util.Calendar
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddSavings : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_savings)

        val amountEdit = findViewById<EditText>(R.id.editAmount1)
        val dateEdit = findViewById<EditText>(R.id.editDate1)
        val addButton = findViewById<Button>(R.id.addButton1)

        dateEdit.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            dateEdit.setOnClickListener {
                val datePickerDialog = DatePickerDialog(
                    this,
                    { _, year, monthOfYear, dayOfMonth ->
                        // Set the selected date in the EditText field
                        val selectedDate = "$year-${monthOfYear + 1}-$dayOfMonth"
                        dateEdit.setText(selectedDate)
                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.show()
            }

            addButton.setOnClickListener {
                val amount = amountEdit.text.toString()
                    val date = dateEdit.text.toString()

                val intent = Intent()
                intent.putExtra("amount", amount)
                intent.putExtra("date", date)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}

