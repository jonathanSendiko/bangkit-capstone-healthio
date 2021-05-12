package com.example.healthio.ui.authentication.signup

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.healthio.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var  binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.btnLogin -> {
                finish()
            }
        }
    }
}