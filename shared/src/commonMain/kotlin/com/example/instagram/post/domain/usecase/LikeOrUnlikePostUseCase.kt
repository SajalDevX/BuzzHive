package com.example.instagram.post.domain.usecase

import com.example.instagram.common.domain.model.Post
import com.example.instagram.common.util.Result
import com.example.instagram.post.domain.PostRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LikeOrUnlikePostUseCase : KoinComponent {
    private val repository by inject<PostRepository>()

    suspend operator fun invoke(post: Post): Result<Boolean> {
        return repository.likeOrUnlikePost(post.postId, !post.isLiked)
    }
}