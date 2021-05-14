package com.panikga.healthio.ui.authentication.signup

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.panikga.healthio.R
import com.panikga.healthio.data.local.entity.User
import com.panikga.healthio.databinding.ActivitySignupBinding
import com.panikga.healthio.ui.authentication.login.LoginActivity

class SignupActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var  binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var awesomeValidation: AwesomeValidation
    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        awesomeValidation = AwesomeValidation(ValidationStyle.BASIC)
        //Input Validation
        awesomeValidation.addValidation(
            this, R.id.inputName,
            "[a-zA-Z\\s]+", R.string.invalid_name
        )
        awesomeValidation.addValidation(
            this, R.id.inputEmail,
            Patterns.EMAIL_ADDRESS, R.string.invalid_email
        )
        awesomeValidation.addValidation(
            this, R.id.inputPassword,
            ".{8,}", R.string.invalid_password
        )
        awesomeValidation.addValidation(
            this, R.id.confirmPassword,
            R.id.inputPassword, R.string.different_password
        )
        binding.btnSignup.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.btnSignup -> {
                val email = binding.inputEmail.text.toString().trim()
                val password = binding.inputPassword.text.toString().trim()
                val repassword = binding.confirmPassword.text.toString().trim()
                if (awesomeValidation.validate()) {
                    if(email.isEmpty()){
                        binding.inputEmail.error = "Email is required, please fill in."
                        binding.inputEmail.requestFocus()
                    }
                    else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        binding.inputEmail.error = "The e-mail address enter is invalid"
                        binding.inputEmail.requestFocus()
                    }
                    else if(password.isEmpty() || password.length < 8){
                        binding.inputPassword.error = "Use at least 8 characters for your secure password"
                        binding.inputPassword.requestFocus()
                    }
                    else if(repassword.isEmpty() || repassword.length < 8){
                        binding.confirmPassword.error = "Use at least 8 characters for your secure password"
                        binding.confirmPassword.requestFocus()
                    }
                    else if(repassword!=password){
                        binding.confirmPassword.error = "Password confirmation doesn\\'t match password, please try it again."
                        binding.confirmPassword.requestFocus()
                    }

                    registerUser(email, password)
                }
            }
            binding.btnLogin -> {
                finish()
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    database = FirebaseDatabase.getInstance()
                    ref = database.getReference("Users")
                    val name = binding.inputName.text.toString().trim()
                    val address = "Please fill in your data"
                    val age = 0
                    val phoneNumber = 0
                    val idCardNumber = 0
                    val user = User(name, address, age, phoneNumber, idCardNumber)
                    val userId = FirebaseAuth.getInstance().currentUser!!.uid

                    ref.child(userId).setValue(user).addOnCompleteListener {
                        Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
                    }
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}