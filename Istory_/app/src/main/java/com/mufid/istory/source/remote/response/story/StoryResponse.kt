package com.mufid.istory.source.remote.response.story

import com.google.gson.annotations.SerializedName

data class StoryResponse(

    @field:SerializedName("listStory")
	val listStory: List<ListStory?>? = null,

    @field:SerializedName("error")
	val error: Boolean? = null,

    @field:SerializedName("message")
	val message: String? = null
)
