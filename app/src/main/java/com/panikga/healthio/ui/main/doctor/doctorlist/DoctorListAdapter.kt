package com.panikga.healthio.ui.main.doctor.doctorlist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.panikga.healthio.data.local.entity.Doctor
import com.panikga.healthio.databinding.ItemDoctorBinding
import com.panikga.healthio.ui.main.doctor.doctordetail.DoctorDetailActivity

class DoctorListAdapter(private val listDoctor: ArrayList<Doctor>) :
    RecyclerView.Adapter<DoctorListAdapter.DoctorListHolder>() {

    private lateinit var mcontext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorListHolder {
        val binding = ItemDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        mcontext = parent.context
        return DoctorListHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorListHolder, position: Int) {
        holder.bind(listDoctor[position])
    }

    override fun getItemCount(): Int = listDoctor.size

    inner class DoctorListHolder(private val binding: ItemDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(doctor: Doctor) {
            with(binding) {
                patient.text = doctor.jumlahpasien
                experience.text = doctor.jumlahpengalaman
                tvName.text = doctor.nama
                tvCategory.text = doctor.spesialisasi

                patient.append("\nPatients")
                experience.append(" years\nExperiences")
//                Glide.with(mcontext)
//                    .load("https://i.imgur.com/L32FF8u.png")
//                    .into(ivPhotos)
                itemView.setOnClickListener {
                    val dataDoctor = Doctor(
                        doctor.jumlahpasien,
                        doctor.jumlahpengalaman,
                        doctor.nama,
                        doctor.photo,
                        doctor.spesialisasi,
                        doctor.rumahsakit
                    )
                    Toast.makeText(mcontext, doctor.nama, Toast.LENGTH_SHORT).show()
                    val i = Intent(mcontext, DoctorDetailActivity::class.java)
                    i.putExtra(DoctorDetailActivity.EXTRA_DOCTOR, dataDoctor)
                    mcontext.startActivity(i)
                }
            }
        }
    }
}