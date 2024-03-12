package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.visperas.rolito.block6.p1.budget_tracking.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar

class AddExpenses : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expenses)

        val descriptionEdit = findViewById<EditText>(R.id.editDescription)
        val amountEdit = findViewById<EditText>(R.id.editAmount)
        val dateEdit = findViewById<EditText>(R.id.editDate)
        val addButton = findViewById<Button>(R.id.addButton)


        dateEdit.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDayOfMonth"
                    dateEdit.setText(selectedDate)
                },
                year,
                month,
                dayOfMonth
            )

            datePickerDialog.show()
        }

        addButton.setOnClickListener {
            val exp = descriptionEdit.text.toString()
            val price = amountEdit.text.toString()
            val date = dateEdit.text.toString()

            val intent = Intent()
            intent.putExtra("description", exp)
            intent.putExtra("amount", price)
            intent.putExtra("date", date)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}

