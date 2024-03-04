package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.content.Intent
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.visperas.rolito.block6.p1.budget_tracking.api.RetrofitClient
import com.visperas.rolito.block6.p1.budget_tracking.databinding.ActivityMainBinding
import com.visperas.rolito.block6.p1.budget_tracking.models.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.StringReader

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registrationText.setOnClickListener {
            startActivity(Intent(this@MainActivity, Registration::class.java))
        }

        binding.loginButton.setOnClickListener {

            val email = binding.loginEmail.text.toString().trim()
            val password = binding.loginPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.loginEmail.error = "Email required"
                binding.loginEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.loginPassword.error = "Password required"
                binding.loginPassword.requestFocus()
                return@setOnClickListener
            }

            val loginDataJson =
                "{\"email\":\"$email\",\"password\":\"$password\"}"

            try {
                val reader = JsonReader(StringReader(loginDataJson))
                reader.isLenient = true
                reader.beginObject()
                reader.close()
                RetrofitClient.instance.loginUser(
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
                                Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_LONG).show()
                                startActivity(
                                    Intent(this@MainActivity, Dashboard::class.java)
                                )

                            } else {
                                val errorMessage: String = try {
                                    response.errorBody()?.string()
                                        ?: "Failed to get a valid response. Response code: ${response.code()}"
                                } catch (e: Exception) {
                                    "Failed to get a valid response. Response code: ${response.code()}"
                                }
                                Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG).show()
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
