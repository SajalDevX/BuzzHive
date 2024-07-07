package com.example.instagram.post.domain

import com.example.instagram.common.domain.model.Post
import com.example.instagram.common.util.Result

interface PostRepository {
    suspend fun getFeedPosts(page: Int, pageSize: Int): Result<List<Post>>
    suspend fun likeOrUnlikePost(postId: Long, shouldLike: Boolean): Result<Boolean>
    suspend fun getUserPosts(userId:Long,page: Int,pageSize: Int):Result<List<Post>>
}