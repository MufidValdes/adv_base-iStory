package com.mufid.istory.source.di

import android.content.Context
import com.mufid.istory.source.IStoryRepository
import com.mufid.istory.source.database.StoryDatabase
import com.mufid.istory.source.remote.response.api.ApiConfig


object Injection {
    fun provideRepository(context: Context): IStoryRepository {
        val apiService = ApiConfig.getApiService()
        val database = StoryDatabase.getDatabase(context)
        return IStoryRepository(apiService, database)
    }
}