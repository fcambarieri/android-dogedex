package com.hackaprende.dogedex

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hackaprende.dogedex.auth.LoginActivity
import com.hackaprende.dogedex.databinding.ActivityMainBinding
import com.hackaprende.dogedex.doglist.DogListActivity
import com.hackaprende.dogedex.model.User
import com.hackaprende.dogedex.setttings.SettingActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.settingsFab.setOnClickListener {
            openSettingsActivity()
        }

        binding.docListFab.setOnClickListener {
            openDogListActivity()
        }

        val user = User.getLoggedInUser(this)
        if (user == null) {
            openLoginActivity()
            return
        }
    }

    private fun openDogListActivity() {
        startActivity(Intent(this, DogListActivity::class.java))
    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, SettingActivity::class.java))
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}