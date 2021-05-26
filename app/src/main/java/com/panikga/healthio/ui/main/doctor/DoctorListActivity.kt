package com.panikga.healthio.ui.main.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.panikga.healthio.R
import com.panikga.healthio.databinding.ActivityDoctorListBinding

class DoctorListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}