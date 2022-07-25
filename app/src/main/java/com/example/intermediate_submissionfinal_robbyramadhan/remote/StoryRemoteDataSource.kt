package com.example.intermediate_submissionfinal_robbyramadhan.remote

import com.example.intermediate_submissionfinal_robbyramadhan.api.ApiStory
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRemoteDataSource(private val apiStory: ApiStory) {

    suspend fun getStories(token: String, page: Int, size: Int) =
        apiStory.getStories("Bearer $token", page, size)

    suspend fun getStoriesWithLocation(token: String, page: Int, size: Int) =
        apiStory.getStories("Bearer $token", page, size, 1)

    suspend fun postStories(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: Double? = null,
        lon: Double? = null
    ) = apiStory.postStory("Bearer $token", file, description, lat, lon)
}