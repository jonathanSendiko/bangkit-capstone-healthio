package com.panikga.healthio.ui.main.history

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.panikga.healthio.data.local.entity.Appointment
import com.panikga.healthio.data.local.entity.Doctor
import com.panikga.healthio.databinding.ItemAppointmentBinding
import com.panikga.healthio.ui.main.doctor.doctordetail.DoctorDetailActivity

class AppointmentListAdapter(private val listAppointment: ArrayList<Appointment>) :
    RecyclerView.Adapter<AppointmentListAdapter.AppointmentListHolder>() {

    private lateinit var auth: FirebaseAuth
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference = database.reference

    private lateinit var mcontext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentListHolder {
        val binding =
            ItemAppointmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        mcontext = parent.context
        return AppointmentListHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentListHolder, position: Int) {
        holder.bind(listAppointment[position])
    }

    override fun getItemCount(): Int = listAppointment.size

    inner class AppointmentListHolder(private val binding: ItemAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(appointment: Appointment) {
            with(binding) {
                spesialisasi.text = appointment.spesialization
                jam.text = appointment.hour
                tanggal.text = appointment.date
                rumahsakit.text = appointment.hospial
                namadokter.text = appointment.doctorName
//                Glide.with(mcontext)
//                    .load("https://i.imgur.com/L32FF8u.png")
//                    .into(ivPhotos)
                delete.setOnClickListener {
                    auth = FirebaseAuth.getInstance()
                    val appointmentref =
                        myRef.child("Users").child(auth.currentUser!!.uid).child("appointment")
                    val id = appointment.id
                    val newref = appointmentref.child(id.toString())
                    newref.child("id").removeValue()
                    newref.child("namaDokter").removeValue()
                    newref.child("spesialisasi").removeValue()
                    newref.child("tanggal").removeValue()
                    newref.child("jam").removeValue()
                    newref.child("rumahsakit").removeValue()
                }
            }
        }
    }
}