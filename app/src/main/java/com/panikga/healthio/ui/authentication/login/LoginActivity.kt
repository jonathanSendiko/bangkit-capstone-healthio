package com.panikga.healthio.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.panikga.healthio.R
import com.panikga.healthio.ui.main.MainActivity
import com.panikga.healthio.ui.authentication.forgotpassword.ForgotPasswordActivity
import com.panikga.healthio.ui.authentication.signup.SignupActivity
import com.panikga.healthio.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        private const val RC_SIGN_IN = 120
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var awesomeValidation: AwesomeValidation
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()
        awesomeValidation = AwesomeValidation(ValidationStyle.BASIC)
        awesomeValidation.addValidation(
            this, R.id.inputEmail,
            Patterns.EMAIL_ADDRESS, R.string.invalid_email
        )
        awesomeValidation.addValidation(
            this, R.id.inputPassword,
            ".{8,}", R.string.wrong_password
        )

        binding.btnSignup.setOnClickListener(this)
        binding.forgotPassword.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        binding.googleLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnLogin -> {
                val email = binding.inputEmail.text.toString().trim()
                val password = binding.inputPassword.text.toString().trim()
                if (awesomeValidation.validate()) {

                    if (email.isEmpty()) {
                        binding.inputEmail.error = "Email is required, please fill in."
                        binding.inputEmail.requestFocus()
                        return@onClick
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        binding.inputEmail.error = R.string.invalid_email.toString()
                        binding.inputEmail.requestFocus()
                        return@onClick
                    } else if (password.isEmpty() || password.length < 8) {
                        binding.inputPassword.error = R.string.invalid_password.toString()
                        binding.inputPassword.requestFocus()
                        return@onClick
                    }
                    loginUser(email, password)
                }
            }
            binding.forgotPassword -> {
                val i = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                startActivity(i)
            }
            binding.btnSignup -> {
                val i = Intent(this@LoginActivity, SignupActivity::class.java)
                startActivity(i)
            }
            binding.googleLogin -> {
                Toast.makeText(applicationContext, "Google Login", Toast.LENGTH_SHORT).show()
                googleSignIn()
            }
        }
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    Toast.makeText(this, "Google sign in success", Toast.LENGTH_SHORT).show()
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                    Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.w("SignInActivity", exception.toString())
                Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInActivity", "signInWithCredential:success")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("SignInActivity", "signInWithCredential:failure")
                    Toast.makeText(this,"signInWithCredential:failure", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}