package com.panikga.healthio.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.panikga.healthio.ui.main.history.HistoryFragment
import com.panikga.healthio.ui.main.home.HomeFragment
import com.panikga.healthio.ui.main.profile.ProfileFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.panikga.healthio.R
import com.panikga.healthio.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

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
        binding.navView.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener {
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