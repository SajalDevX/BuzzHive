package com.example.instagram.follows.domain

import com.example.instagram.common.domain.model.FollowsUser
import com.example.instagram.common.util.Result

interface FollowsRepository {
    suspend fun getFollowableUsers(): Result<List<FollowsUser>>
    suspend fun followOrUnfollow(followedUserId: Long, shouldFollow: Boolean): Result<Boolean>
}