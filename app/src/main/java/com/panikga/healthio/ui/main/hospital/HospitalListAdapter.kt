package com.panikga.healthio.ui.main.hospital

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.panikga.healthio.data.local.entity.Hospital
import com.panikga.healthio.databinding.RowHospitalBinding
import com.panikga.healthio.ui.main.doctor.DoctorListActivity
import com.panikga.healthio.ui.main.profile.EditProfileActivity

class HospitalListAdapter(private val listHospital: ArrayList<Hospital>) :
    RecyclerView.Adapter<HospitalListAdapter.HospitalListHolder>() {


    private lateinit var mcontext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalListHolder {
        val binding = RowHospitalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        mcontext = parent.context
        return HospitalListHolder(binding)
    }

    override fun onBindViewHolder(holder: HospitalListHolder, position: Int) {
        holder.bind(listHospital[position])
    }

    override fun getItemCount(): Int = listHospital.size

    inner class HospitalListHolder(private val binding: RowHospitalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hospital: Hospital) {
            with(binding) {
                nama.text = hospital.nama
                alamat.text = hospital.alamat
                ratingAvg.text = hospital.rating_avg
                ratingCount.text = hospital.rating_count
                itemView.setOnClickListener {
                    Toast.makeText(mcontext, hospital.nama, Toast.LENGTH_SHORT).show()
                    val intent = Intent(mcontext, DoctorListActivity::class.java)
                    mcontext.startActivity(intent)
                }
            }
        }
    }
}