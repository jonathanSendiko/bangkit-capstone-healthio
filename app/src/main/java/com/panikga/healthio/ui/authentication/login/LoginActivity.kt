package com.panikga.healthio.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.panikga.healthio.ui.main.MainActivity
import com.panikga.healthio.ui.authentication.forgotpassword.ForgotPasswordActivity
import com.panikga.healthio.ui.authentication.signup.SignupActivity
import com.panikga.healthio.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()


        binding.btnSignup.setOnClickListener(this)
        binding.forgotPassword.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.btnLogin -> {
                val email = binding.inputEmail.text.toString().trim()
                val password = binding.inputPassword.text.toString().trim()
                loginUser(email, password)
            }
            binding.forgotPassword -> {
                val i = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                startActivity(i)
            }
            binding.btnSignup -> {
                val i = Intent(this@LoginActivity, SignupActivity::class.java)
                startActivity(i)
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}