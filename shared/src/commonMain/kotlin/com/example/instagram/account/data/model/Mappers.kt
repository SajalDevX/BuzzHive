package com.example.instagram.account.data.model

import com.example.instagram.account.domain.model.Profile
import com.example.instagram.common.data.local.UserSettings

fun UserSettings.toDomainProfile():Profile{
    return Profile(
        id = id,
        name = name,
        bio = bio,
        imageUrl = avatar!!,
        followersCount = followersCount,
        followingCount = followingCount,
        isFollowing = false,
        isOwnProfile = true
    )
}
fun Profile.toUserSettings(token:String):UserSettings{
    return UserSettings(
        id = id,
        name = name,
        bio = bio,
        avatar = imageUrl,
        token = token,
        followersCount = followersCount,
        followingCount = followingCount
    )
}