package com.mufid.istory.ui.map

import androidx.lifecycle.ViewModel
import com.mufid.istory.source.IStoryRepository

class MapsViewModel(private val storyRepo: IStoryRepository) : ViewModel() {
    fun getStories(token: String) = storyRepo.getStoriesLocation(token)
}