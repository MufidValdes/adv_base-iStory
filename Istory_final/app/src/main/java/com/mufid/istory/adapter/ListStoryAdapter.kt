package com.mufid.istory.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mufid.istory.R
import com.mufid.istory.databinding.ItemStoryBinding
import com.mufid.istory.source.remote.response.story.Story
import com.mufid.istory.ui.detail.DetailActivity
import com.mufid.istory.utils.setLocalDateFormat

class ListStoryAdapter :
    PagingDataAdapter<Story, ListStoryAdapter.MyViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    class MyViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, data: Story) {
            binding.apply {
                tvUsername.text = data.name

                tvDescription.text = data.description
                tvCreatedAt.setLocalDateFormat(data.createdAt)
                itemView.setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_STORY, data)
                    context.startActivity(intent)
                }
            }
            Glide.with(itemView.context)
                .load(data.photoUrl)
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_error)
                .into(binding.imgAvatar)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(holder.itemView.context, data)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}