package com.example.instagram.auth.domain.repository

import com.example.instagram.auth.domain.model.AuthResultData
import com.example.instagram.common.util.Result

internal interface AuthRepository {

    suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): Result<AuthResultData>

    suspend fun signIn(email: String, password: String): Result<AuthResultData>
}