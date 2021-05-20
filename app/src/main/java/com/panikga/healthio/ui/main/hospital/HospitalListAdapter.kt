package com.panikga.healthio.ui.main.hospital

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.panikga.healthio.data.local.entity.Hospital
import com.panikga.healthio.databinding.ItemHospitalBinding

class HospitalListAdapter(private val listHospital: ArrayList<Hospital>) : RecyclerView.Adapter<HospitalListAdapter.HospitalListHolder>() {


    private lateinit var mcontext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalListHolder {
        val binding = ItemHospitalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        mcontext = parent.context
        return HospitalListHolder(binding)
    }

    override fun onBindViewHolder(holder: HospitalListHolder, position: Int) {
        holder.bind(listHospital[position])
    }

    override fun getItemCount(): Int = listHospital.size

    inner class HospitalListHolder(private val binding: ItemHospitalBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(hospital: Hospital){
            with(binding){
                tvHospitalName.text = hospital.nama
                tvCategory.text = hospital.jenis
                itemView.setOnClickListener {
                    Toast.makeText(mcontext, hospital.nama, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}