package com.panikga.healthio.ui.main.doctor.doctorlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.panikga.healthio.data.local.entity.Doctor
import com.panikga.healthio.databinding.ActivityDoctorListBinding

class DoctorListActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_HOSPITALNAME = "extra_hospitalname"
    }

    private lateinit var binding: ActivityDoctorListBinding

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference = database.reference

    private var doctorList: ArrayList<Doctor> = arrayListOf()
    private lateinit var doctorListAdapter: DoctorListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvDoctor.setHasFixedSize(true)
        showRecyclerList()
        addFromFirebase(doctorList)
        binding.arrow.setOnClickListener{
            finish()
        }
    }

    private fun showRecyclerList() {
        binding.rvDoctor.layoutManager = LinearLayoutManager(this)
        doctorListAdapter = DoctorListAdapter(doctorList)
        binding.rvDoctor.adapter = doctorListAdapter
    }

    private fun addFromFirebase(list: ArrayList<Doctor>) {
        val hospitalName = intent.getStringExtra(EXTRA_HOSPITALNAME)
        binding.title.text = hospitalName

        myRef.child("rumahsakit")
            .orderByChild("nama_rs")
            .equalTo(hospitalName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for (key in snapshot.children) {
                        val doctorref = key.child("dokter")
                        for (doc in doctorref.children) {
                            val doctor = Doctor()
                            doctor.jumlahpasien = doc.child("jumlahpasien").value.toString()
                            doctor.jumlahpengalaman =
                                doc.child("jumlahpengalaman").value.toString()
                            doctor.nama = doc.child("nama").value.toString()
                            doctor.photo = doc.child("photo").value.toString()
                            doctor.spesialisasi = doc.child("spesialisasi").value.toString()
                            doctor.rumahsakit = hospitalName
                            list.add(doctor)
                        }
                    }
                    doctorListAdapter.notifyDataSetChanged()
                }
            })
    }
}