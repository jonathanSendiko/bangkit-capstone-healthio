package com.panikga.healthio.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.R
import com.panikga.healthio.databinding.FragmentProfileBinding
import com.panikga.healthio.ui.authentication.login.LoginActivity
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

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        auth = FirebaseAuth.getInstance()

        binding.btnLogout.setOnClickListener(this)
//        profileViewModel.text.observe(viewLifecycleOwner, Observer {
//            binding.textNotifications.text = it
//        })
        return binding.root
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.btnLogout -> {
                alert("Are you sure want to logout?"){
                    noButton {  }
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