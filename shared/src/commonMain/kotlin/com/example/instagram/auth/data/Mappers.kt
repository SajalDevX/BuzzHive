package com.example.instagram.auth.data

import com.example.instagram.auth.domain.model.AuthResultData

internal fun AuthResponseData.toAuthResultData(): AuthResultData {
    return AuthResultData(id, name, bio, avatar, token, followersCount, followingCount)
}