package com.mufid.istory.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mufid.istory.source.IStoryRepository
import com.mufid.istory.source.UserRepository
import com.mufid.istory.source.remote.response.story.Story
import kotlinx.coroutines.launch

class MainViewModel(private val userRepo: UserRepository, private val storyRepo: IStoryRepository) : ViewModel() {

    fun getToken() : LiveData<String> {
        return userRepo.getToken().asLiveData()
    }

    fun isLogin() : LiveData<Boolean>{
        return userRepo.isLogin().asLiveData()
    }



    fun getStories(token: String) : LiveData<PagingData<Story>> =
        storyRepo.getStories(token).cachedIn(viewModelScope)
}