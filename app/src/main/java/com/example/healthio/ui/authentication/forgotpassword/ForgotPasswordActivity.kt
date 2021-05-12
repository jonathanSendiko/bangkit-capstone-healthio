package com.example.healthio.ui.authentication.forgotpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.healthio.R
import com.example.healthio.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToSignIn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.backToSignIn -> {
                finish()
            }
        }
    }
}