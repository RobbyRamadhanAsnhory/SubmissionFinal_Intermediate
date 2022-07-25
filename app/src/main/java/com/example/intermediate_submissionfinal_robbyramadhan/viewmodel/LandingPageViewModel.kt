package com.example.intermediate_submissionfinal_robbyramadhan.viewmodel

import androidx.lifecycle.*
import com.example.intermediate_submissionfinal_robbyramadhan.notice.AuthError
import com.example.intermediate_submissionfinal_robbyramadhan.repository.UserRepository
import kotlinx.coroutines.launch

class LandingPageViewModel(private val userRepository: UserRepository) : ViewModel() {

    var userItem = userRepository.userItems.asLiveData()

    private val _isLoading = MutableLiveData<Boolean>()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun login(email: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                userRepository.login(email, password)

            } catch (e: AuthError) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}