package com.mufid.istory.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.mufid.istory.source.database.StoryDatabase
import com.mufid.istory.source.database.StoryRemoteMediator
import com.mufid.istory.source.remote.response.api.ApiService
import com.mufid.istory.source.remote.response.story.Story
import com.mufid.istory.source.remote.response.story.StoryResponse
import com.mufid.istory.source.remote.response.upload.UploadResponse
import com.mufid.istory.utils.Results
import com.mufid.istory.utils.wrapEspressoIdlingResource
import okhttp3.MultipartBody
import okhttp3.RequestBody

class IStoryRepository(private val apiService: ApiService, private val storyDatabase: StoryDatabase) {

    fun getStories(token: String): LiveData<PagingData<Story>> {
        wrapEspressoIdlingResource {
            @OptIn(ExperimentalPagingApi::class)
            return Pager(
                config = PagingConfig(
                    pageSize = 5
                ),
                remoteMediator = StoryRemoteMediator(storyDatabase, apiService, token),
                pagingSourceFactory = {
                    storyDatabase.storyDao().getAllStories()
                }
            ).liveData
        }
    }

    fun getStoriesLocation(token: String): LiveData<Results<StoryResponse>> = liveData {
        emit(Results.Loading)
        try {
            val client = apiService.getAllStory("Bearer $token", location = 1)
            emit(Results.Success(client))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }

    fun uploadStory(
        token: String,
        imageMultipart: MultipartBody.Part,
        desc: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ): LiveData<Results<UploadResponse>> = liveData {
        emit(Results.Loading)
        try {
            val client = apiService.uploadStory("Bearer $token", imageMultipart, desc, lat, lon)
            emit(Results.Success(client))
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }
}