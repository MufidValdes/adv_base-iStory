package com.mufid.istory.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mufid.istory.source.IStoryRepository
import com.mufid.istory.source.UserRepository
import com.mufid.istory.source.di.Injection
import com.mufid.istory.source.di.UserInjection
import com.mufid.istory.ui.main.MainViewModel
import com.mufid.istory.ui.map.MapsViewModel
import com.mufid.istory.ui.profile.SettingViewModel
import com.mufid.istory.ui.story.StoryViewModel

class StoryViewModelFactory private constructor(private val userRepo: UserRepository, private val storyRepo: IStoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userRepo, storyRepo) as T
            }
            modelClass.isAssignableFrom(StoryViewModel::class.java) -> {
                StoryViewModel(storyRepo) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(storyRepo) as T
            }

            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: StoryViewModelFactory? = null
        fun getInstance(context: Context): StoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: StoryViewModelFactory(UserInjection.provideRepository(context), Injection.provideRepository(context))
            }.also { instance = it }
    }
}