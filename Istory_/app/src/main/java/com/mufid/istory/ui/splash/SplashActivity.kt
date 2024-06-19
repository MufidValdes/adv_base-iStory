package com.mufid.istory.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.mufid.istory.ui.authentication.AuthenticationActivity
import com.mufid.istory.ui.authentication.AuthenticationViewModel
import com.mufid.istory.ui.main.MainActivity
import com.mufid.istory.viewmodel.ViewModelFactory
import com.mufid.istory.R
import com.mufid.istory.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding
    private lateinit var factory: ViewModelFactory
    private val viewModel: AuthenticationViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        factory = ViewModelFactory.getInstance(this)
        setUpView()
    }


    private fun setUpView() {
        viewModel.isFirstTime().observe(this) { isFirst ->
            if (isFirst) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, AuthenticationActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 2000L)
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 2000L)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}