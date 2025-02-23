package com.mufid.istory.source.remote.response.user

import com.google.gson.annotations.SerializedName

data class LoginResult(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)