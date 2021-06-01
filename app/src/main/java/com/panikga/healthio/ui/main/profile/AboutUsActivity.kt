package com.panikga.healthio.ui.main.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.panikga.healthio.R
import com.panikga.healthio.ui.authentication.login.LoginActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
    }

//    override fun onClick(v: View?) {
//        when (v) {
//            binding.icbackabt.set {
//                icbackabt.setOnClickListener {
//                    val intent = Intent(this, ProfileFragment::class.java)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    startActivity(intent)
//                    finish()
//                }
//            }
        }






