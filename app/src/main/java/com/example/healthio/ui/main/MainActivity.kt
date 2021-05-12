package com.example.healthio.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.healthio.R
import com.example.healthio.databinding.ActivityMainBinding
import com.example.healthio.ui.main.history.HistoryFragment
import com.example.healthio.ui.main.home.HomeFragment
import com.example.healthio.ui.main.profile.ProfileFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar


class MainActivity : AppCompatActivity() {


    private lateinit var navView: ChipNavigationBar
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navView.setItemSelected(R.id.navigation_home, true)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment()).commit()

        bottomMenu()
    }

    private fun bottomMenu() {
        navView.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(id: Int) {
                var fragment: Fragment? = null
                when (id) {
                    R.id.navigation_home -> fragment = HomeFragment()
                    R.id.navigation_history -> fragment = HistoryFragment()
                    R.id.navigation_profile -> fragment = ProfileFragment()
                }
                if (fragment != null) {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.fragment_container, fragment
                        ).commit()
                }
            }
        })
    }
}