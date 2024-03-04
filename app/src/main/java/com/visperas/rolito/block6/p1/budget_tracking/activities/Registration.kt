package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.content.Intent
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.visperas.rolito.block6.p1.budget_tracking.api.RetrofitClient
import com.visperas.rolito.block6.p1.budget_tracking.databinding.ActivityRegistrationBinding
import com.visperas.rolito.block6.p1.budget_tracking.models.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.StringReader

class Registration : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.registrationButton.setOnClickListener {

            val name = binding.registrationName.text.toString().trim()
            val email = binding.registrationEmail.text.toString().trim()
            val password = binding.registrationPassword.text.toString().trim()

            if (name.isEmpty()) {
                binding.registrationName.error = "Username required"
                binding.registrationName.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                binding.registrationPassword.error = "Email required"
                binding.registrationPassword.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.registrationPassword.error = "Password required"
                binding.registrationPassword.requestFocus()
                return@setOnClickListener
            }

            val RegistrationDataJson =
                "{\"name\":\"$name\",\"email\":\"$email\",\"password\":\"$password\"}"


            try {
                val reader = JsonReader(StringReader(RegistrationDataJson))
                reader.isLenient = true
                reader.beginObject()
                reader.close()
                RetrofitClient.instance.registrationUser(
                    name,
                    email,
                    password
                )
                    .enqueue(object : Callback<DefaultResponse> {
                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(
                            call: Call<DefaultResponse>,
                            response: Response<DefaultResponse>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                Toast.makeText(
                                    applicationContext,
                                    response.body()!!.message,
                                    Toast.LENGTH_LONG
                                ).show()
                                val intent = Intent(this@Registration, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            } else {
                                val errorMessage: String = try {
                                    response.errorBody()?.string()
                                        ?: "Failed to get a valid response. Response code: ${response.code()}"
                                } catch (e: Exception) {
                                    "Failed to get a valid response. Response code: ${response.code()}"
                                }
                                Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG)
                                    .show()
                                Log.e("API_RESPONSE", errorMessage)
                            }
                        }
                    })
            } catch (e: Exception) {
                Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }
}

