package com.panikga.healthio.ui.main.doctor.doctordetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.panikga.healthio.R
import com.panikga.healthio.data.local.entity.Category
import com.panikga.healthio.data.local.entity.Doctor
import com.panikga.healthio.databinding.ActivityDoctorDetailBinding
import com.panikga.healthio.ui.main.hospital.HospitalListActivity

class DoctorDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DOCTOR = "extra_doctor"
    }
    private lateinit var binding : ActivityDoctorDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataDoctor = intent.getParcelableExtra<Doctor>(EXTRA_DOCTOR) as Doctor
        binding.tvName.text = dataDoctor.nama
        binding.spesialisasi.text = dataDoctor.spesialisasi
        binding.experience.text = dataDoctor.jumlahpengalaman
        binding.patient.text = dataDoctor.jumlahpasien
    }
}