package com.mufid.istory.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufid.istory.source.UserRepository
import kotlinx.coroutines.launch

class SettingViewModel (private val userRepo: UserRepository) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            userRepo.logout()
        }
    }
}