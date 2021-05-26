package com.panikga.healthio.ui.main.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.panikga.healthio.R
import com.panikga.healthio.data.local.entity.User
import com.panikga.healthio.databinding.ActivityEditProfileBinding
import com.panikga.healthio.databinding.ActivitySignupBinding
import com.panikga.healthio.ui.authentication.login.LoginActivity
import com.panikga.healthio.ui.main.MainActivity
import org.w3c.dom.Text

class EditProfileActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var  binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var awesomeValidation: AwesomeValidation
    lateinit var ref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        awesomeValidation = AwesomeValidation(ValidationStyle.BASIC)
        awesomeValidation.addValidation(
            this, R.id.editName,
            "[a-zA-Z\\s]+", R.string.invalid_name
        )

        binding.saveChanges.setOnClickListener(this)

        val user = auth.currentUser
        binding.editEmail.setText(user.email)

    }

    override fun onClick(v: View?) {
        when(v){
            binding.saveChanges -> {
                val name = binding.editName.text.toString().trim()
                val age = binding.editAge.text.toString().toInt()
                val phoneNumber = binding.editPhoneNumber.text.toString().toInt()
                val idCard = binding.editIdCard.text.toString().toInt()
                if(awesomeValidation.validate()){
                    if(name.isEmpty()){
                        binding.editName.error = "Name is Required"
                        binding.editName.requestFocus()
                    }
                }
                updateProfile(name,age,phoneNumber,idCard)
            }
        }
    }
    private fun updateProfile(name: String, age: Int, phoneNumber: Int, idCardNumber: Int) {
        // [START update_profile]
        val user = Firebase.auth.currentUser
        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }
        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    database = FirebaseDatabase.getInstance()
                    ref = database.getReference("Users")
                    val name = binding.editName.text.toString().trim()
                    val address = "Villa Taman Cibodas"
                    val age = binding.editAge.text.toString().toInt()
                    val phoneNumber = binding.editPhoneNumber.text.toString().toInt()
                    val idCardNumber = binding.editIdCard.text.toString().toInt()
                    val userInstance = User(name, address, age, phoneNumber, idCardNumber)
                    val userId = FirebaseAuth.getInstance().currentUser!!.uid
                    ref.child(userId).setValue(userInstance).addOnCompleteListener {
                        Toast.makeText(this, "User Update Successful", Toast.LENGTH_SHORT).show()
                    }
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        // [END update_profile]
    }
}



