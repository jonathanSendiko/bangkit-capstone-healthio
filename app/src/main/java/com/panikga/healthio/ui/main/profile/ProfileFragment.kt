package com.panikga.healthio.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.panikga.healthio.databinding.FragmentProfileBinding
import com.panikga.healthio.ui.authentication.login.LoginActivity
import com.panikga.healthio.ui.maps.MapsActivity
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton

class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("110335627867-65vm2uji7qkcodh67l849k1912gtre61.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val acct = GoogleSignIn.getLastSignedInAccount(activity)

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        auth = FirebaseAuth.getInstance()
        if (acct != null) {
            binding.name.text = acct.displayName
            binding.email.text = acct.email
        } else {
            // Write a message to the database
            val database = FirebaseDatabase.getInstance()
            val myRef = database.reference
            // Read from the database
            myRef.child("Users")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        binding.name.text =
                            dataSnapshot.child("${FirebaseAuth.getInstance().currentUser!!.uid}/name").value.toString()
                        binding.email.text = FirebaseAuth.getInstance().currentUser!!.email
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                        Toast.makeText(activity, "failed", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        binding.personalInformation.setOnClickListener(this)
        binding.information.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.personalInformation -> {
                val intent = Intent(context, EditProfileActivity::class.java)
                startActivity(intent)
            }
            binding.information -> {
                val intent = Intent(context, AboutUsActivity::class.java)
                startActivity(intent)
            }
            binding.btnLogout -> {
                alert("Are you sure want to logout?") {
                    noButton { }
                    yesButton {
                        auth.signOut()
                        googleSignInClient.signOut()
                        val intent = Intent(activity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }.show()
            }
        }
    }
}