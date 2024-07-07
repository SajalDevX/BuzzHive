package com.example.instagram.account.data.repository

import com.example.instagram.account.data.AccountApiService
import com.example.instagram.account.data.model.toDomainProfile
import com.example.instagram.account.data.model.toUserSettings
import com.example.instagram.account.domain.model.Profile
import com.example.instagram.account.domain.repository.ProfileRepository
import com.example.instagram.common.data.local.UserPreferences
import com.example.instagram.common.util.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.instagram.common.util.Result
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

internal class ProfileRepositoryImpl(
    private val accountApiService: AccountApiService,
    private val preferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : ProfileRepository {
    override fun getProfile(profileId: Long): Flow<Result<Profile>> {
        return flow {
            val userData = preferences.getUserData()
            if (profileId == userData.id) {
                emit(Result.Success(userData.toDomainProfile()))
            }
            val apiResponse = accountApiService.getProfile(
                token = userData.token,
                profileId = profileId,
                currentUserId = userData.id
            )
            when (apiResponse.code) {
                HttpStatusCode.OK -> {
                    val profile = apiResponse.data.profile!!.toProfile()

                    if (profileId == userData.id) {
                        preferences.setUserData(profile.toUserSettings(userData.token))
                    }
                    emit(Result.Success(profile))
                }

                else -> {
                    emit(Result.Error(message = "Error: ${apiResponse.data.message}"))
                }
            }
        }.catch { exception ->
            emit(Result.Error(message = "Error: $exception"))
        }.flowOn(dispatcher.io)
    }
}