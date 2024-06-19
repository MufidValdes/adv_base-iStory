package com.mufid.istory.source.di

import android.content.Context
import com.mufid.istory.source.IStoryRepository
import com.mufid.istory.source.local.LocalDataSource
import com.mufid.istory.source.local.datastore.LoginPreference
import com.mufid.istory.source.remote.RemoteDataSource
import com.mufid.istory.ui.main.dataStore


object Injection {
    fun provideRepository(context: Context): IStoryRepository {

        val loginPreference = LoginPreference.getInstance(context.dataStore)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(loginPreference)
        return IStoryRepository.getInstance(remoteDataSource, localDataSource)
    }
}