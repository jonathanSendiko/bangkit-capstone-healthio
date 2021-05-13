package com.panikga.healthio.ui.authentication.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.panikga.healthio.data.local.entity.User
import com.panikga.healthio.databinding.ActivitySignupBinding
import com.panikga.healthio.ui.authentication.login.LoginActivity

class SignupActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var  binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        binding.btnSignup.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.btnSignup -> {
                val email = binding.inputEmail.text.toString().trim()
                val password = binding.inputPassword.text.toString().trim()
                registerUser(email, password)
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
                    val nama = binding.inputName.text.toString().trim()
                    val address = "Please fill in your data"
                    val interest = "Please fill in your data"
                    val user = User(nama,address,interest)
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