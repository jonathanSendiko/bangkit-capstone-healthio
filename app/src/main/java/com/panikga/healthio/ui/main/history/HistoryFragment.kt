package com.panikga.healthio.ui.main.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.panikga.healthio.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    private lateinit var historyViewModel: HistoryViewModel

    private lateinit var binding: FragmentHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)

        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        historyViewModel.text.observe(
            viewLifecycleOwner,
            Observer { binding.textDashboard.text = it })
        return binding.root
    }
}