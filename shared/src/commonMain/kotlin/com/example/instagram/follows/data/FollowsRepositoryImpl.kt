package com.example.instagram.follows.data

import com.example.instagram.common.data.local.UserPreferences
import com.example.instagram.common.data.model.FollowsParams
import com.example.instagram.common.data.remote.FollowsApiService
import com.example.instagram.common.domain.model.FollowsUser
import com.example.instagram.common.util.Constants
import com.example.instagram.common.util.DispatcherProvider
import com.example.instagram.common.util.Result
import com.example.instagram.follows.domain.FollowsRepository
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withContext
import okio.IOException

internal class FollowsRepositoryImpl(
    private val followsApiService: FollowsApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : FollowsRepository {
    override suspend fun getFollowableUsers(): Result<List<FollowsUser>> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = followsApiService.getFollowableUsers(userData.token, userData.id)

                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(data = apiResponse.data.follows.map { it.toDomainFollowUser() })
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }

                    HttpStatusCode.Forbidden -> {
                        Result.Success(data = emptyList())
                    }

                    else -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = Constants.NO_INTERNET_ERROR)
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun followOrUnfollow(
        followedUserId: Long,
        shouldFollow: Boolean
    ): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val followsParams = FollowsParams(userData.id, followedUserId)
                val apiResponse = if (shouldFollow) {
                    followsApiService.followUser(userData.token, followsParams)
                } else {
                    followsApiService.unfollowUser(userData.token, followsParams)
                }

                if (apiResponse.code == HttpStatusCode.OK) {
                    Result.Success(data = apiResponse.data.success)
                } else {
                    Result.Error(data = false, message = "${apiResponse.data.success}")
                }
            } catch (ioException: IOException) {
                Result.Error(message = Constants.NO_INTERNET_ERROR)
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }
}