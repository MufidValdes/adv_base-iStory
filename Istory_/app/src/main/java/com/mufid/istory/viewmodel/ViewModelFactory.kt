package com.mufid.istory.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mufid.istory.source.IStoryRepository
import com.mufid.istory.source.di.Injection
import com.mufid.istory.ui.authentication.AuthenticationViewModel
import com.mufid.istory.ui.main.MainViewModel

class ViewModelFactory private constructor(private val iStoryRepository: IStoryRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthenticationViewModel::class.java) -> {
                AuthenticationViewModel(iStoryRepository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(iStoryRepository) as T
            }
            else -> throw Throwable("ViewModel Tidak ada : "+modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }
}