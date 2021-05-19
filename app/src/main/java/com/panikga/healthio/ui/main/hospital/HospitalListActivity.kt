package com.panikga.healthio.ui.main.hospital

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.panikga.healthio.data.local.entity.Category
import com.panikga.healthio.data.local.entity.Hospital
import com.panikga.healthio.data.local.entity.User
import com.panikga.healthio.databinding.ActivityHospitalListBinding

class HospitalListActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_HOSPITAL = "extra_hospital"
    }
    private lateinit var binding : ActivityHospitalListBinding

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference = database.reference

    private var hospitalList: ArrayList<Hospital> = arrayListOf()
    private lateinit var hospitalListAdapter: HospitalListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvHospital.setHasFixedSize(true)
        showRecyclerList()
        addFromFirebase(hospitalList)
    }

    private fun showRecyclerList() {
        binding.rvHospital.layoutManager = LinearLayoutManager(this)
        hospitalListAdapter = HospitalListAdapter(hospitalList)
        binding.rvHospital.adapter = hospitalListAdapter
    }

    private fun addFromFirebase(list: ArrayList<Hospital>){
        val dataUser = intent.getParcelableExtra<Category>(EXTRA_HOSPITAL) as Category

        myRef.child("rumahsakit")
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for (key in snapshot.children){
                        val hospital = Hospital()
                        if (key.child("jenis").value.toString() == dataUser.categoryName){
                            hospital.hospitalName = key.child("nama_rs").value.toString()
                            hospital.categoryName = key.child("jenis").value.toString()
                            list.add(hospital)
                        }
                    }
                    hospitalListAdapter.notifyDataSetChanged()
                }
            })
    }
}