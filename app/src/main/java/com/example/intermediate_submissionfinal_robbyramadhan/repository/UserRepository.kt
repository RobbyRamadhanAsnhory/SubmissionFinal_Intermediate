package com.example.intermediate_submissionfinal_robbyramadhan.repository

import android.content.Context
import com.example.intermediate_submissionfinal_robbyramadhan.R
import com.example.intermediate_submissionfinal_robbyramadhan.model.UserModel
import com.example.intermediate_submissionfinal_robbyramadhan.UserPreference
import com.example.intermediate_submissionfinal_robbyramadhan.notice.AuthError
import com.example.intermediate_submissionfinal_robbyramadhan.remote.UserRemoteDataSource

class UserRepository(

    private val context: Context,
    private val userRemote: UserRemoteDataSource,
    private val prefs: UserPreference
) {

    val userItems = prefs.getUser()

    suspend fun login(email: String, password: String) {
        try {
            val responses = userRemote.login(email, password)
            if (responses.isSuccessful && responses.body() != null) {
                val result = responses.body()?.loginResult
                result?.let {
                    val user = UserModel(
                        id = it.userId,
                        email = email,
                        name = it.name,
                        password = password,
                        token = it.token,
                        isLoggedIn = true
                    )
                    prefs.updateUser(user)
                }
            } else {
                throw AuthError(context.getString(R.string.fail_login))
            }
        } catch (e: Throwable) {
            throw AuthError(e.message.toString())
        }
    }

    suspend fun register(name: String, email: String, password: String) {
        try {
            val responses = userRemote.register(name, email, password)
            if (!responses.isSuccessful) {
                throw AuthError(
                    responses.body()?.message ?: context.getString(R.string.fail_signup)
                )
            }
        } catch (e: Throwable) {
            throw AuthError(e.message.toString())
        }
    }

    suspend fun logout() {
        try {
            prefs.updateUser(UserModel(token = null, isLoggedIn = false))
        } catch (e: Throwable) {
            throw AuthError(e.message.toString())
        }
    }
}