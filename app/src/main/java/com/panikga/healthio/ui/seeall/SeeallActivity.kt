package com.panikga.healthio.ui.seeall

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.panikga.healthio.R
import com.panikga.healthio.data.local.entity.Hospital

class SeeallActivity: AppCompatActivity() {
    private var list: ArrayList<Hospital> = arrayListOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListHospitalAdapter

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeall)
        addHospital(list)
        onCreateComponent()
        initView()
    }

    private fun addHospital(list: ArrayList<Hospital>){
        // LIMIT X BERARTI NAMPILIN X RS
        val queryRef = myRef.child("rumahsakit").orderByChild("score").limitToLast(5)

        queryRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()

                for (key in snapshot.children){
                    val hospital = Hospital()

                    hospital.nama = key.child("nama_rs").value.toString()
                    hospital.alamat = key.child("alamat").value.toString()
                    hospital.lat = key.child("lat").value.toString()
                    hospital.long = key.child("long").value.toString()
                    hospital.rating_avg = key.child("rating_average").value.toString()
                    hospital.rating_count = key.child("rating_count").value.toString()
                    hospital.kelas = key.child("kelas").value.toString()
                    hospital.jenis = key.child("jenis").value.toString()

                    list.add(hospital)
                }
                adapter.notifyDataSetChanged()
            }

        })


//        myRef.child("rumahsakit")
//            .addListenerForSingleValueEvent(object: ValueEventListener {
//                override fun onCancelled(error: DatabaseError) {}
//
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    list.clear()
//
//                    val queryRef = myRef.orderByChild("score").limitToLast(5)
//
//                    for (i in 1..3){
//                        val hospital = Hospital()
//
//                        hospital.nama = snapshot.child(i.toString()).child("nama_rs").value.toString()
//                        hospital.alamat = snapshot.child(i.toString()).child("alamat").value.toString()
//                        hospital.lat = snapshot.child(i.toString()).child("lat").value.toString()
//                        hospital.long = snapshot.child(i.toString()).child("long").value.toString()
//                        hospital.rating_avg = snapshot.child(i.toString()).child("rating_average").value.toString()
//                        hospital.rating_count = snapshot.child(i.toString()).child("rating_count").value.toString()
//                        hospital.kelas = snapshot.child(i.toString()).child("kelas").value.toString()
//                        hospital.jenis = snapshot.child(i.toString()).child("jenis").value.toString()
//
//                        list.add(hospital)
//                    }
//                    adapter.notifyDataSetChanged()
//                }
//            })
    }

    private fun onCreateComponent(){
        adapter = ListHospitalAdapter(list)
    }

    private fun initView(){
        initializeRecyclerView()
    }

    private fun initializeRecyclerView(){
        recyclerView = findViewById(R.id.rv_hospital)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        adapter.setOnItemClickCallBack(object : ListHospitalAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Hospital) {
                Toast.makeText(applicationContext, data.nama, Toast.LENGTH_SHORT).show()
            }
        })
    }

}