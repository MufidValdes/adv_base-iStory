package com.mufid.istory.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufid.istory.source.IStoryRepository
import com.mufid.istory.source.remote.response.user.LoginResponse
import com.mufid.istory.source.remote.response.user.RegisterResponse
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val iStoryRepository: IStoryRepository): ViewModel() {

    fun login(email: String, password: String): LiveData<LoginResponse> =
        iStoryRepository.login(email, password)

    fun register(name:String, email: String, password: String): LiveData<RegisterResponse> =
        iStoryRepository.register(name, email, password)

    fun saveUserToken(token: String) = viewModelScope.launch {
        iStoryRepository.saveUserToken(token)
    }

    fun isFirstTime(): LiveData<Boolean> =
        iStoryRepository.getIsFirstTime()

    fun setIsFirstTime(isFirstTime: Boolean) = viewModelScope.launch {
        iStoryRepository.saveIsFirstTime(isFirstTime)
    }

    fun logOut() = viewModelScope.launch {
        iStoryRepository.clearCache()
    }
}