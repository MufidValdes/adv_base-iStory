package com.mufid.istory.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mufid.istory.R
import com.mufid.istory.databinding.ActivityDetailBinding
import com.mufid.istory.source.remote.response.story.Story
import com.mufid.istory.ui.main.MainActivity
import com.mufid.istory.utils.setLocalDateFormat

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Story Detail"
        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val story = intent.getParcelableExtra<Story>(EXTRA_STORY)
        binding.apply {
            tvUsername.text = story?.name
            tvCreatedAt.setLocalDateFormat(story?.createdAt.toString())
            tvDescription.text = story?.description
        }
        Glide.with(this)
            .load(story?.photoUrl)
            .placeholder(R.drawable.image_loading)
            .error(R.drawable.image_error)
            .into(binding.imgAvatar)
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}