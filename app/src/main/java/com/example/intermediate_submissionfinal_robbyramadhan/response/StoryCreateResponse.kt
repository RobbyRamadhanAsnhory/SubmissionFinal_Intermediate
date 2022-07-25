package com.example.intermediate_submissionfinal_robbyramadhan.response

import com.google.gson.annotations.SerializedName

data class StoryCreateResponse(
    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)
