package com.example.intermediate_submissionfinal_robbyramadhan.viewmodel

import androidx.lifecycle.*
import com.example.intermediate_submissionfinal_robbyramadhan.model.StoryModel
import com.example.intermediate_submissionfinal_robbyramadhan.notice.StoryError
import com.example.intermediate_submissionfinal_robbyramadhan.repository.StoryRepository
import com.example.intermediate_submissionfinal_robbyramadhan.repository.UserRepository
import kotlinx.coroutines.launch

class StoryWithMapsViewModel(
    userRepository: UserRepository,
    private val storyRepository: StoryRepository
) :
    ViewModel() {

    val userItems = userRepository.userItems.asLiveData()

    private val _stories = MutableLiveData<List<StoryModel>>()
    val stories: LiveData<List<StoryModel>> = _stories

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getStoriesWithLocation(token: String) {
        viewModelScope.launch {
            try {
                val stories = storyRepository.getStoriesWithLocation(token = token)
                _stories.value = stories
            } catch (e: StoryError) {
                _errorMessage.value = e.message.toString()
            }
        }
    }

}