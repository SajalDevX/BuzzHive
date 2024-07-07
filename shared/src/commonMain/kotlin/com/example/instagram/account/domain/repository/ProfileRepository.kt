package com.example.instagram.account.domain.repository

import com.example.instagram.account.domain.model.Profile
import kotlinx.coroutines.flow.Flow
import com.example.instagram.common.util.Result

interface ProfileRepository {
    fun getProfile(profileId:Long): Flow<Result<Profile>>
}