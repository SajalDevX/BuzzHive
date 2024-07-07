package com.example.instagram.account.domain.usecase

import com.example.instagram.account.domain.model.Profile
import com.example.instagram.account.domain.repository.ProfileRepository
import com.example.instagram.common.util.Result
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetProfileUseCase : KoinComponent {
    private val repository: ProfileRepository by inject()
    operator fun invoke(profileId: Long): Flow<Result<Profile>> {
        return repository.getProfile(profileId)
    }
}