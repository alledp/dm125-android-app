package com.aduilio.mytasks.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.aduilio.mytasks.R
import com.aduilio.mytasks.databinding.ActivityPreferenceBinding
import com.aduilio.mytasks.fragment.PreferenceFragment

class PreferenceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreferenceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
                .replace(R.id.layoutContainer, PreferenceFragment())
                .commit()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}