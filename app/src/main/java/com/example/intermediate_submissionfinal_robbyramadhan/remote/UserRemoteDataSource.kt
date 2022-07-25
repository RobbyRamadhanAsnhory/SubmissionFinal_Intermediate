package com.example.intermediate_submissionfinal_robbyramadhan.remote

import com.example.intermediate_submissionfinal_robbyramadhan.api.ApiUser

class UserRemoteDataSource(private val apiUser: ApiUser) {

    suspend fun login(email: String, password: String) = apiUser.login(email, password)

    suspend fun register(name: String, email: String, password: String) =
        apiUser.register(name, email, password)
}