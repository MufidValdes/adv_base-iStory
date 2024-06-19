package com.mufid.istory.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mufid.istory.source.IStoryRepository
import com.mufid.istory.source.remote.response.story.StoryResponse
import com.mufid.istory.source.remote.response.upload.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MainViewModel(private val iStoryRepository: IStoryRepository): ViewModel() {

    private val _token = MutableLiveData<String>()
    private val token: LiveData<String> get() = _token

    fun setToken(token: String) {
        _token.value = token
    }

    fun getUserToken(): LiveData<String> = iStoryRepository.getUserToken()

    val getAllStory: LiveData<StoryResponse> = Transformations.switchMap(token) {
        iStoryRepository.getAllStory(it)
    }

    fun uploadStory(token: String, photo: MultipartBody.Part, desc: RequestBody): LiveData<UploadResponse> =
        iStoryRepository.uploadStory(token, photo, desc)
}