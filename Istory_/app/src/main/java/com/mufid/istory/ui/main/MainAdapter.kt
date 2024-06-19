package com.mufid.istory.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mufid.istory.source.remote.response.story.ListStory
import com.mufid.istory.utils.DateUtil.toAnotherDate
import com.mufid.istory.databinding.ItemRowStoriesBinding
import com.mufid.istory.ui.main.home.HomeFragmentDirections

class MainAdapter: RecyclerView.Adapter<MainAdapter.StoryViewHolder>() {

    private val listStory = ArrayList<ListStory>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemRowStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = listStory[position]
        holder.bind(story)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ListStory>) {
        listStory.clear()
        listStory.addAll(data)
        notifyDataSetChanged()
    }



    override fun getItemCount(): Int = listStory.size

    inner class StoryViewHolder(private val binding: ItemRowStoriesBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStory) {
            binding.apply {
                tvItemName.text = story.name
                tvDesc.text = story.description
                tvDate.text = story.createdAt?.toAnotherDate()
                itemView.setOnClickListener {
                    val toDetail = HomeFragmentDirections.actionHomeFragmentToDetailFragment(story)
                    it.findNavController().navigate(toDetail)
                }
                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .into(imgItemStory)
            }
        }
    }
}