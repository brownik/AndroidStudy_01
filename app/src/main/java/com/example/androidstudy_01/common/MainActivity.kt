package com.example.androidstudy_01.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidstudy_01.R
import com.example.androidstudy_01.databinding.ActivityMainBinding
import com.example.androidstudy_01.list_adapter.RouletteFragmentUseListAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        addOnClickListener()
    }

    private fun addOnClickListener() = with(binding) {
        btnRoulette.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.flRoulette, RouletteFragmentUseListAdapter())
                .commit()
        }
    }
}