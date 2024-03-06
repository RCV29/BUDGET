package com.visperas.rolito.block6.p1.budget_tracking.activities

// SplashScreenActivity.kt
import android.annotation.SuppressLint
import android.content.Intent
import android.widget.ImageView
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.visperas.rolito.block6.p1.budget_tracking.R


class SplashScreen : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH = 4000 // 2 seconds

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ativity_splash)

        val imageViewSplash = findViewById<ImageView>(R.id.splashscreen_icon_view)
        Glide.with(this)
            .load(R.drawable.splash_coin)
            .into(imageViewSplash)

        Handler().postDelayed({
            // Start login activity after splash screen duration
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}
