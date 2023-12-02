package com.thet.waitingtime.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.thet.waitingtime.databinding.ActivityMainBinding
import com.thet.waitingtime.model.Doctor

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var doctorList: ArrayList<Doctor> = arrayListOf()
    private var updateDoctorList: ArrayList<Doctor> = arrayListOf()


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculate.isEnabled = false
        binding.addDoctor.setOnClickListener {
            binding.calculate.isEnabled = true
            try {
                val time = binding.ctime.text.toString().toInt()
                val doctor = Doctor(binding.name.text.toString(), time)
                doctorList.add(doctor)
                Toast.makeText(this, "Number of Doctors : ${doctorList.size}", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: Exception) {
                Toast.makeText(this, "You need to add consultation time! ", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        binding.calculate.setOnClickListener {
            updateDoctorList.clear()
            updateDoctorList = doctorList
            val position = binding.position.text.toString().toInt()

            if (position <= doctorList.size) {
                binding.waitingTime.text = "Waiting Time = 0"
            } else {
                for (i in 1 until position) {
                    if (i > updateDoctorList.size) {
                        updateDoctorList = updateDoctorList.sortedWith(compareBy { it.time })
                            .toMutableList() as ArrayList<Doctor>
                        for (d in doctorList) {
                            if (d.name == updateDoctorList[0].name) {
                                val dd = Doctor(d.name, updateDoctorList[0].time + d.time)
                                updateDoctorList[0] = dd
                            }
                        }
                    }
                }
                updateDoctorList = updateDoctorList.sortedWith(compareBy { it.time })
                    .toMutableList() as ArrayList<Doctor>
                binding.waitingTime.text = "Waiting Time = ${updateDoctorList[0].time}"
            }
        }
    }
}