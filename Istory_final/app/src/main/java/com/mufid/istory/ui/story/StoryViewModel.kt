package com.mufid.istory.ui.story

import androidx.lifecycle.ViewModel
import com.mufid.istory.source.IStoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryViewModel(private val storyRepo: IStoryRepository) : ViewModel() {

    fun uploadStory(
        token: String,
        imageMultipart: MultipartBody.Part,
        desc: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ) = storyRepo.uploadStory(token, imageMultipart, desc, lat, lon)
}