package com.mufid.istory.ui.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mufid.istory.R
import com.mufid.istory.adapter.ListStoryAdapter
import com.mufid.istory.adapter.LoadingStateAdapter
import com.mufid.istory.databinding.ActivityMainBinding
import com.mufid.istory.ui.StoryViewModelFactory
import com.mufid.istory.ui.login.LoginActivity
import com.mufid.istory.ui.map.MapsActivity
import com.mufid.istory.ui.profile.SettingActivity
import com.mufid.istory.ui.story.StoryActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvStories.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvStories.layoutManager = LinearLayoutManager(this)
        }

        title = "Story"
        setupViewModel()
        setupAction()
    }

    private fun setupAction() {

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId){
                R.id.addStory -> {
                    val intent = Intent(this, StoryActivity::class.java)
                    intent.putExtra(StoryActivity.EXTRA_TOKEN, token)
                    startActivity(intent)
                    true
                }

                R.id.setting -> {
                    startActivity(Intent(this, SettingActivity::class.java))
                    finish()
                    true
                }
                else -> true
            }
        }
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.map_menu -> {
                    val intent = Intent(this, MapsActivity::class.java)
                    intent.putExtra(MapsActivity.EXTRA_TOKEN, token)
                    startActivity(intent)
                    true
                }
                else -> true
            }
        }
        return
    }

    private fun setupViewModel() {
        val factory: StoryViewModelFactory = StoryViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(
            this,
            factory
        )[MainViewModel::class.java]

        mainViewModel.isLogin().observe(this){
            if (!it){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        mainViewModel.getToken().observe(this){ token ->
            this.token = token
            if (token.isNotEmpty()){
                val adapter = ListStoryAdapter()
                binding.rvStories.adapter = adapter.withLoadStateFooter(
                    footer = LoadingStateAdapter {
                        adapter.retry()
                    }
                )
                mainViewModel.getStories(token).observe(this){result ->
                    adapter.submitData(lifecycle, result)
                }
            }
        }
    }
}
