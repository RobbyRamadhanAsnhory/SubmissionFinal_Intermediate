package com.example.intermediate_submissionfinal_robbyramadhan.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.intermediate_submissionfinal_robbyramadhan.Injection
import com.example.intermediate_submissionfinal_robbyramadhan.repository.StoryRepository
import com.example.intermediate_submissionfinal_robbyramadhan.repository.UserRepository
import com.example.intermediate_submissionfinal_robbyramadhan.viewmodel.*

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val storyRepository: StoryRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LandingPageViewModel::class.java) -> {
                LandingPageViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(StoryViewModel::class.java) -> {
                StoryViewModel(userRepository, storyRepository) as T
            }
            modelClass.isAssignableFrom(StoryWithMapsViewModel::class.java) -> {
                StoryWithMapsViewModel(userRepository, storyRepository) as T
            }
            modelClass.isAssignableFrom(PostStoryViewModel::class.java) -> {
                PostStoryViewModel(userRepository, storyRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideUserRepository(context),
                    Injection.provideStoryRepository(context)
                )
            }.also { instance = it }
    }
}