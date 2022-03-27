package com.luckytrip.luckytrip.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.luckytrip.luckytrip.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_destinations.*

@AndroidEntryPoint
class DestinationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinations)

        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}