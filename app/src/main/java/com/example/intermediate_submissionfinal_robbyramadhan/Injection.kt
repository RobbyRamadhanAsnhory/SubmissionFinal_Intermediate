package com.example.intermediate_submissionfinal_robbyramadhan

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.intermediate_submissionfinal_robbyramadhan.api.ApiConfig
import com.example.intermediate_submissionfinal_robbyramadhan.database.StoryDatabase
import com.example.intermediate_submissionfinal_robbyramadhan.remote.StoryRemoteDataSource
import com.example.intermediate_submissionfinal_robbyramadhan.remote.UserRemoteDataSource
import com.example.intermediate_submissionfinal_robbyramadhan.repository.StoryRepository
import com.example.intermediate_submissionfinal_robbyramadhan.repository.UserRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object Injection {

    fun provideUserRepository(context: Context): UserRepository {
        val userApiInterface = ApiConfig.getUserApi()
        val userRemoteDataSource = UserRemoteDataSource(userApiInterface)
        return UserRepository(
            context,
            userRemoteDataSource,
            UserPreference.getInstance(context.dataStore)
        )
    }

    fun provideStoryRepository(context: Context): StoryRepository {
        val storyApiInterface = ApiConfig.getStoryApi()
        val storyRemoteDataSource = StoryRemoteDataSource(storyApiInterface)
        return StoryRepository(storyRemoteDataSource, StoryDatabase.getDatabase(context))
    }
}