package com.example.instagram.android.common.dummy_data

data class Comment(
    val id: String,
    val comment: String,
    val date: String,
    val authorName: String,
    val authorImageUrl: String,
    val authorId: Int,
    val postId: String
)

val sampleComments = listOf(
    Comment(
        id = "comment1",
        date = "2023-06-24",
        comment = "Great samplePost!\nI learned a lot from it.",
        authorName = sampleUsers[0].name,
        authorImageUrl = sampleUsers[0].profileUrl,
        authorId = sampleUsers[0].id,
        postId = sampleSamplePosts[0].id
    ),
    Comment(
        id = "comment2",
        date = "2023-06-24",
        comment = "Nice work! Keep sharing more content like this.",
        authorName = sampleUsers[1].name,
        authorImageUrl = sampleUsers[1].profileUrl,
        authorId = sampleUsers[1].id,
        postId = sampleSamplePosts[0].id
    ),
    Comment(
        id = "comment3",
        date = "2023-06-24",
        comment = "Thanks for the insights!\nYour samplePost was really helpful.",
        authorName = sampleUsers[2].name,
        authorImageUrl = sampleUsers[2].profileUrl,
        authorId = sampleUsers[2].id,
        postId = sampleSamplePosts[0].id
    ),
    Comment(
        id = "comment4",
        date = "2023-06-24",
        comment = "I enjoyed reading your samplePost! Looking forward to more.",
        authorName = sampleUsers[3].name,
        authorImageUrl = sampleUsers[3].profileUrl,
        authorId = sampleUsers[3].id,
        postId = sampleSamplePosts[0].id
    )
)
