package com.mufid.istory.ui.profile

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mufid.istory.databinding.ActivitySettingBinding
import com.mufid.istory.ui.UserViewModelFactory
import com.mufid.istory.ui.login.LoginActivity
import com.mufid.istory.ui.main.MainActivity

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var settingViewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        setupAction()
        setupViewModel()
    }
    private fun setupAction() {
        binding.textBahasa.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        binding.tvLogout.setOnClickListener {
            settingViewModel.logout()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
    private fun setupViewModel() {
        val factory: UserViewModelFactory = UserViewModelFactory.getInstance(this)
        settingViewModel = ViewModelProvider(this, factory)[SettingViewModel::class.java]
    }

}