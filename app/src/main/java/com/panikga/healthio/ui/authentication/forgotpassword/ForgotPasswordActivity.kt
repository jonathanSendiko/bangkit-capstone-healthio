package com.panikga.healthio.ui.authentication.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.google.firebase.auth.FirebaseAuth
import com.panikga.healthio.R
import com.panikga.healthio.databinding.ActivityForgotPasswordBinding
import com.panikga.healthio.ui.authentication.login.LoginActivity

class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var awesomeValidation: AwesomeValidation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        awesomeValidation = AwesomeValidation(ValidationStyle.BASIC)
        //Input Validation
        //Input Validation
        awesomeValidation.addValidation(
            this, R.id.inputAddress,
            Patterns.EMAIL_ADDRESS, R.string.invalid_email
        )

        binding.btnReset.setOnClickListener(this)
        binding.backToSignIn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnReset -> {
                if (awesomeValidation.validate()) {
                    val auth = FirebaseAuth.getInstance()
                    var emailAddress = binding.inputAddress.text.toString().trim()
                    auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    applicationContext,
                                    "Reset Password Success",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val i =
                                    Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
                                startActivity(i)
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "Reset Password Failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
            binding.backToSignIn -> {
                finish()
            }
        }
    }
}