package com.panikga.healthio.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.panikga.healthio.data.local.entity.Category
import com.panikga.healthio.data.local.entity.Hospital
import com.panikga.healthio.databinding.FragmentHomeBinding
import com.panikga.healthio.ui.main.home.category.CategoryAdapter
import com.panikga.healthio.ui.main.home.category.CategoryData
import com.panikga.healthio.ui.seeall.ListHospitalAdapter
import com.panikga.healthio.ui.seeall.SeeallActivity

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var categoryList: ArrayList<Category> = arrayListOf()
    private lateinit var binding: FragmentHomeBinding

    private var hospitalList: ArrayList<Hospital> = arrayListOf()
    private lateinit var hospitalAdapter: ListHospitalAdapter

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference = database.reference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        //category
        binding.rvCategory.setHasFixedSize(true)
        categoryList.addAll(CategoryData.listData)

        //recommendation
        addHospital(hospitalList)
        hospitalAdapter = ListHospitalAdapter(hospitalList)

        showRecylerView()

        binding.seeAllHospital.setOnClickListener(this)
        return binding.root
    }

    private fun showRecylerView() {
        binding.rvCategory.layoutManager = LinearLayoutManager(context)
        binding.rvCategory.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val categoryAdapter = CategoryAdapter(categoryList)
        binding.rvCategory.adapter = categoryAdapter

        val layoutManager = LinearLayoutManager(context)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.rvHospital.layoutManager = layoutManager
        binding.rvHospital.adapter = hospitalAdapter

        hospitalAdapter.setOnItemClickCallBack(object : ListHospitalAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Hospital) {
                Toast.makeText(context, data.nama, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addHospital(list: ArrayList<Hospital>) {
        // LIMIT X BERARTI NAMPILIN X RS
        val queryRef = myRef.child("rumahsakit").orderByChild("score").limitToLast(5)

        queryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()

                for (key in snapshot.children) {
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
                hospitalAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.seeAllHospital -> {
                val intent = Intent(activity, SeeallActivity::class.java)
                startActivity(intent)
            }
        }
    }
}