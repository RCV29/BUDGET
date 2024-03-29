package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.visperas.rolito.block6.p1.budget_tracking.api.RetrofitClient
import com.visperas.rolito.block6.p1.budget_tracking.databinding.ActivityMainBinding
import com.visperas.rolito.block6.p1.budget_tracking.models.LoginRequest
import com.visperas.rolito.block6.p1.budget_tracking.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

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

            val loginRequest = LoginRequest(email, password)

            RetrofitClient.instance.login(loginRequest)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            val token = response.body()?.token

                            token?.let {
                                saveTokenToSharedPreferences(it)
                            }

                            startActivity(Intent(this@MainActivity, Dashboard::class.java))
                            finish()
                        } else {
                            Log.e("MyTag", "Login Failed: ${response.raw()}")

                        }
                    }

                    override fun onFailure(
                        call: Call<LoginResponse>,
                        t: Throwable
                    ) {
                        Log.e("MyTag", "Network Failed: $call")
                    }
                })
        }
    }

    private fun saveTokenToSharedPreferences(token: String) {
        with(sharedPreferences.edit()) {
            putString("token", token)
            apply()
        }
    }
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
    }
}