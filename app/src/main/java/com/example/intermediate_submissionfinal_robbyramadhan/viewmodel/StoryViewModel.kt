package com.example.intermediate_submissionfinal_robbyramadhan.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.intermediate_submissionfinal_robbyramadhan.model.StoryModel
import com.example.intermediate_submissionfinal_robbyramadhan.notice.AuthError
import com.example.intermediate_submissionfinal_robbyramadhan.repository.StoryRepository
import com.example.intermediate_submissionfinal_robbyramadhan.repository.UserRepository
import kotlinx.coroutines.launch

class StoryViewModel(
    private val userRepository: UserRepository,
    private val storyRepository: StoryRepository
) : ViewModel() {

    val userItems = userRepository.userItems.asLiveData()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val stories: LiveData<PagingData<StoryModel>> =
        userItems.switchMap {
            storyRepository.getStories(
                it.token ?: ""
            ).cachedIn(viewModelScope)
        }

    fun logout() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                userRepository.logout()
            } catch (e: AuthError) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}