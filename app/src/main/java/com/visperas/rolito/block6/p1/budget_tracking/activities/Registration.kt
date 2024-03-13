package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.visperas.rolito.block6.p1.budget_tracking.R
import com.visperas.rolito.block6.p1.budget_tracking.api.RetrofitClient
import com.visperas.rolito.block6.p1.budget_tracking.databinding.ActivityRegistrationBinding
import com.visperas.rolito.block6.p1.budget_tracking.models.RegisterRequest
import com.visperas.rolito.block6.p1.budget_tracking.models.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Registration : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolBar = findViewById<ImageView>(R.id.left_icon)

        toolBar.setOnClickListener {
            onBackPressed()
        }


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

            val registerRequest = RegisterRequest(name,email, password)

            RetrofitClient.instance.registration(registerRequest)
                .enqueue(object : Callback<RegisterResponse>{
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        if (response.isSuccessful) {
                            Log.e(
                                "MyTag",
                                "Registration Success: $call"
                            )

                            startActivity(Intent(this@Registration, MainActivity::class.java))
                            finish()
                        } else {
                            Log.e("MyTag", "Registration Failed: $call || ${response.raw()}")

                        }
                    }

                    override fun onFailure(
                        call: Call<RegisterResponse>,
                        t: Throwable
                    ) {
                        Log.e("MyTag", "Network Failed: $call")
                    }
                })
        }
    }
}

