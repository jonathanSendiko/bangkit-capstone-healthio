package com.panikga.healthio.ui.main.doctor.doctorappointment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.panikga.healthio.R
import com.panikga.healthio.data.local.entity.Doctor
import com.panikga.healthio.databinding.ActivityDoctorAppointmentBinding
import com.panikga.healthio.ui.main.MainActivity
import com.panikga.healthio.ui.main.home.HomeFragment
import java.text.DateFormat
import java.util.*

class DoctorAppointmentActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_BOOK = "extra_book"
    }

    private lateinit var binding: ActivityDoctorAppointmentBinding
    private lateinit var hour: String

    private lateinit var auth: FirebaseAuth
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hour = ""
        val dataDoctor = intent.getParcelableExtra<Doctor>(EXTRA_BOOK) as Doctor
        binding.tvName.text = dataDoctor.nama
        binding.spesialisasi.text = dataDoctor.spesialisasi
        binding.rumahsakit.text = dataDoctor.rumahsakit

        auth = FirebaseAuth.getInstance()


        val calendar = Calendar.getInstance()

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // set the calendar date as calendar view selected date
            calendar.set(year, month, dayOfMonth)

            // set this date as calendar view selected date
            binding.calendarView.date = calendar.timeInMillis
        }

        binding.button.setOnClickListener {

            if (hour == "") {
                Toast.makeText(this, "Please select your meeting time.", Toast.LENGTH_SHORT).show()
            } else {
                val selectedDate: Long = binding.calendarView.date
                calendar.timeInMillis = selectedDate
                val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
                Toast.makeText(
                    this,
                    dateFormatter.format(calendar.time) + " " + hour,
                    Toast.LENGTH_SHORT
                ).show()

                val appointment =
                    myRef.child("Users").child(auth.currentUser!!.uid).child("appointment")
                val id = appointment.push().key
                val newref = appointment.child(id.toString())
                newref.child("id").setValue(id)
                newref.child("namaDokter").setValue(dataDoctor.nama)
                newref.child("spesialisasi").setValue(dataDoctor.spesialisasi)
                newref.child("tanggal").setValue(dateFormatter.format(calendar.time))
                newref.child("jam").setValue(hour)
                newref.child("rumahsakit").setValue(dataDoctor.rumahsakit)

                val i = Intent(this@DoctorAppointmentActivity, MainActivity::class.java)
                startActivity(i)
            }
        }

        binding.arrow.setOnClickListener {
            finish()
        }
        binding.option1.setOnClickListener(this)
        binding.option2.setOnClickListener(this)
        binding.option3.setOnClickListener(this)
        binding.option4.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.option1 -> {
                binding.option1.setBackgroundResource(R.drawable.ic_rectangle_321)
                binding.option2.setBackgroundResource(R.color.white)
                binding.option3.setBackgroundResource(R.color.white)
                binding.option4.setBackgroundResource(R.color.white)
                hour = binding.option1.text.toString()
            }
            binding.option2 -> {
                binding.option1.setBackgroundResource(R.color.white)
                binding.option2.setBackgroundResource(R.drawable.ic_rectangle_321)
                binding.option3.setBackgroundResource(R.color.white)
                binding.option4.setBackgroundResource(R.color.white)
                hour = binding.option2.text.toString()
            }
            binding.option3 -> {
                binding.option1.setBackgroundResource(R.color.white)
                binding.option2.setBackgroundResource(R.color.white)
                binding.option3.setBackgroundResource(R.drawable.ic_rectangle_321)
                binding.option4.setBackgroundResource(R.color.white)
                hour = binding.option3.text.toString()
            }
            binding.option4 -> {
                binding.option1.setBackgroundResource(R.color.white)
                binding.option2.setBackgroundResource(R.color.white)
                binding.option3.setBackgroundResource(R.color.white)
                binding.option4.setBackgroundResource(R.drawable.ic_rectangle_321)
                hour = binding.option4.text.toString()
            }
        }
    }
}