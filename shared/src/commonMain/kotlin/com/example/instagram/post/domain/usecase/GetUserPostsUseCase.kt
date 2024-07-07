package com.example.instagram.post.domain.usecase

import com.example.instagram.common.domain.model.Post
import com.example.instagram.common.util.Result
import com.example.instagram.post.domain.PostRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetUserPostsUseCase : KoinComponent {
    private val repository: PostRepository by inject()
    suspend operator fun invoke(userId: Long, page: Int, pageSize: Int): Result<List<Post>> {
        return repository.getUserPosts(userId, page, pageSize)
    }
}