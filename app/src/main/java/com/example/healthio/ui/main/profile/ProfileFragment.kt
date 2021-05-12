package com.example.healthio.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.healthio.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        profileViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textNotifications.text = it
        })
        return binding.root
    }
}