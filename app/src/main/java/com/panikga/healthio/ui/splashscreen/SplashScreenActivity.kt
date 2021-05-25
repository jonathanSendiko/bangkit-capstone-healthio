package com.panikga.healthio.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.panikga.healthio.R
import com.panikga.healthio.ui.main.MainActivity
import com.panikga.healthio.ui.onboarding.OnboardingActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        Handler(mainLooper).postDelayed({
            if (auth.currentUser != null) {
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashScreenActivity, OnboardingActivity::class.java)
                startActivity(intent)
            }
        }, 2000)
    }
}