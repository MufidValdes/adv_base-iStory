package com.mufid.istory.ui.signup

import androidx.lifecycle.ViewModel
import com.mufid.istory.source.UserRepository

class SignupViewModel(private val repo: UserRepository) : ViewModel() {

    fun register(name: String, email: String, password: String) = repo.register(name, email, password)
}