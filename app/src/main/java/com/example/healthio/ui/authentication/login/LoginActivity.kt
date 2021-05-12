package com.example.healthio.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.healthio.ui.main.MainActivity
import com.example.healthio.databinding.ActivityLoginBinding
import com.example.healthio.ui.authentication.signup.SignupActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnSignup.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.btnLogin -> {
                val i = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(i)
            }
            binding.btnSignup -> {
                val i = Intent(this@LoginActivity, SignupActivity::class.java)
                startActivity(i)
            }
        }
    }
}