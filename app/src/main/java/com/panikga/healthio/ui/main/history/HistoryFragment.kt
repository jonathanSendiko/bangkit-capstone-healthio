package com.panikga.healthio.ui.main.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.panikga.healthio.data.local.entity.Appointment
import com.panikga.healthio.data.local.entity.Doctor
import com.panikga.healthio.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    private lateinit var historyViewModel: HistoryViewModel

    private lateinit var binding: FragmentHistoryBinding


    private var appointmentList: ArrayList<Appointment> = arrayListOf()
    private lateinit var appointmentListAdapter: AppointmentListAdapter

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference = database.reference
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)

        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        historyViewModel.text.observe(viewLifecycleOwner, Observer {
//            binding.textDashboard.text = it
        })

        auth = FirebaseAuth.getInstance()
        binding.rvAppointment.setHasFixedSize(true)
        showRecyclerList()
        addFromFirebase(appointmentList)
        return binding.root
    }

    private fun showRecyclerList() {
        binding.rvAppointment.layoutManager = LinearLayoutManager(activity)
        appointmentListAdapter = AppointmentListAdapter(appointmentList)
        binding.rvAppointment.adapter = appointmentListAdapter
    }

    private fun addFromFirebase(list: ArrayList<Appointment>) {
        myRef.child("Users").child(auth.currentUser.uid).child("appointment")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for (key in snapshot.children) {
                        val appointment = Appointment()
                        appointment.id = key.child("id").value.toString()
                        appointment.doctorName = key.child("namaDokter").value.toString()
                        appointment.spesialization = key.child("spesialisasi").value.toString()
                        appointment.hour = key.child("jam").value.toString()
                        appointment.date = key.child("tanggal").value.toString()
                        appointment.hospial = key.child("rumahsakit").value.toString()
                        list.add(appointment)
                    }
                    appointmentListAdapter.notifyDataSetChanged()
                }
            })
    }
}