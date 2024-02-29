package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.visperas.rolito.block6.p1.budget_tracking.R
import com.visperas.rolito.block6.p1.budget_tracking.api.RetrofitClient
import com.visperas.rolito.block6.p1.budget_tracking.models.LoginResponse
import com.visperas.rolito.block6.p1.budget_tracking.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val registrationText: TextView = findViewById(R.id.registration_text)
        val loginButton: Button = findViewById(R.id.loginButton)
        val loginEmail: EditText = findViewById(R.id.login_email)
        val loginPassword: EditText = findViewById(R.id.login_password)
        val showPasswordCheckBox: CheckBox = findViewById(R.id.showPasswordCheckBox)

        registrationText.setOnClickListener {
            startActivity(Intent(this@MainActivity, Registration::class.java))
        }

        loginButton.setOnClickListener {

            val email = loginEmail.text.toString().trim()
            val password = loginPassword.text.toString().trim()

            if (email.isEmpty()) {
                loginEmail.error = "Email required"
                loginEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                loginPassword.error = "Password required"
                loginPassword.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.login(email, password)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if ((response.body()?.error ?: false) == false) {

                            SharedPrefManager.getInstance(applicationContext)
                                .saveUser(response.body()?.user!!)

                            val intent = Intent(applicationContext, Dashboard::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                            startActivity(intent)


                        } else {
                            Toast.makeText(
                                applicationContext,
                                response.body()?.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                })

        }
    }

    override fun onStart() {
        super.onStart()

        if (SharedPrefManager.getInstance(this).isLoggedIn) {
            val intent = Intent(applicationContext, Dashboard::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}
