package com.visperas.rolito.block6.p1.budget_tracking.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.visperas.rolito.block6.p1.budget_tracking.R
import com.visperas.rolito.block6.p1.budget_tracking.models.DefaultResponse
import com.visperas.rolito.block6.p1.budget_tracking.api.RetrofitClient
import com.visperas.rolito.block6.p1.budget_tracking.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Registration : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val registrationUsername: EditText = findViewById(R.id.registration_email)
        val registrationEmail: EditText = findViewById(R.id.registration_password1)
        val registrationPassword: EditText = findViewById(R.id.registration_password2)
        val registrationButton: Button = findViewById(R.id.registrationButton)
        val showPasswordCheckBox1: CheckBox = findViewById(R.id.showPasswordCheckBoxx)

        showPasswordCheckBox1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                registrationEmail.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                registrationPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                registrationEmail.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                registrationPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }

        registrationButton.setOnClickListener{

            val name = registrationUsername.text.toString().trim()
            val email = registrationEmail.text.toString().trim()
            val password = registrationPassword.text.toString().trim()

            if(name.isEmpty()){
                registrationUsername.error = "Username required"
                registrationUsername.requestFocus()
                return@setOnClickListener
            }

            if(email.isEmpty()){
                registrationEmail.error = "Email required"
                registrationEmail.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                registrationPassword.error = "Password required"
                registrationPassword.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.registration(name,email,password)
                .enqueue(object: Callback<DefaultResponse>{
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        Toast.makeText(applicationContext, response.body()?.success,Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(applicationContext,t.message,Toast.LENGTH_LONG).show()
                    }

                })


        }


    }

    override fun onStart() {
        super.onStart()

        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext,Dashboard::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}