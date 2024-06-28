package com.example.instagram.android.common.components

import android.content.ContentValues.TAG
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.instagram.android.R
import com.example.instagram.android.common.dummy_data.SamplePost
import com.example.instagram.android.common.dummy_data.samplePosts
import com.example.instagram.android.common.theming.Black54
import com.example.instagram.android.common.theming.DarkGray
import com.example.instagram.android.common.theming.InstagramTheme
import com.example.instagram.android.common.theming.LargeSpacing
import com.example.instagram.android.common.theming.LightGray
import com.example.instagram.android.common.theming.MediumSpacing
import com.example.instagram.android.common.util.toCurrentUrl
import com.example.instagram.common.domain.model.Post

@Composable
fun PostListItem(
    modifier: Modifier = Modifier,
    post: Post,
    onPostClick: (Post) -> Unit,
    onProfileClick: (userId: Long) -> Unit,
    onLikeClick: (Post) -> Unit,
    onCommentClick: (Post) -> Unit,
    isDetailScreen: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(ratio = 0.7f)
            .background(color = MaterialTheme.colors.surface)
            .clickable { onPostClick(post) }
    ) {
        PostHeader(
            name = post.userName,
            profileUrl = post.userImageUrl,
            date = post.createdAt,
            onProfileClick = {
                onProfileClick(
                    post.userId
                )
            }
        )
        Log.e("Post Image", "The image url is: ${post.imageUrl.toCurrentUrl()}")
        AsyncImage(
            model = post.imageUrl.toCurrentUrl(),
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 1.0f),
            contentScale = ContentScale.Crop,
            placeholder = if (MaterialTheme.colors.isLight) {
                painterResource(id = R.drawable.light_image_place_holder)
            } else {
                painterResource(id = R.drawable.dark_image_place_holder)
            }
        )

        PostLikesRow(
            likesCount = post.likesCount,
            commentCount = post.commentsCount,
            onLikeClick = { onLikeClick(post) },
            onCommentClick = { onCommentClick(post) },
            isPostLiked = post.isLiked

        )

        Text(
            text = post.caption,
            style = MaterialTheme.typography.body2,
            modifier = modifier.padding(horizontal = LargeSpacing),
            maxLines = if (isDetailScreen) 10 else 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
fun PostHeader(
    modifier: Modifier = Modifier,
    name: String,
    profileUrl: String?,
    date: String,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = LargeSpacing,
                vertical = MediumSpacing
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
    ) {
        CircleImage(
            modifier = modifier.size(30.dp),
            url = profileUrl?.toCurrentUrl(),
            onClick = onProfileClick
        )

        Text(
            text = name,
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onSurface
        )

        Box(
            modifier = modifier
                .size(4.dp)
                .clip(CircleShape)
                .background(
                    color = if (MaterialTheme.colors.isLight) {
                        LightGray
                    } else {
                        DarkGray
                    }
                )
        )

        Text(
            text = date,
            style = MaterialTheme.typography.caption.copy(
                textAlign = TextAlign.Start,
                fontSize = 12.sp,
                color = if (MaterialTheme.colors.isLight) {
                    LightGray
                } else {
                    DarkGray
                }
            ),
            modifier = modifier.weight(1f)
        )

        Icon(
            painter = painterResource(id = R.drawable.round_more_horizontal),
            contentDescription = null,
            tint = if (MaterialTheme.colors.isLight) {
                LightGray
            } else {
                DarkGray
            }
        )
    }
}


@Composable
fun PostLikesRow(
    modifier: Modifier = Modifier,
    likesCount: Int,
    commentCount: Int,
    isPostLiked: Boolean,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 0.dp,
                horizontal = MediumSpacing
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onLikeClick
        ) {
            Icon(
                painter = if (!isPostLiked) {
                    painterResource(id = R.drawable.like_icon_outlined)
                } else {
                    painterResource(id = R.drawable.like_icon_filled)
                },
                contentDescription = null,
                tint = if (isPostLiked) {
                    Red
                } else {
                    DarkGray
                }
            )
        }

        Text(
            text = "$likesCount",
            style = MaterialTheme.typography.subtitle2.copy(fontSize = 18.sp)
        )

        Spacer(modifier = modifier.width(MediumSpacing))

        IconButton(
            onClick = onCommentClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.chat_icon_outlined),
                contentDescription = null,
                tint = if (MaterialTheme.colors.isLight) {
                    LightGray
                } else {
                    DarkGray
                }
            )
        }

        Text(
            text = "$commentCount",
            style = MaterialTheme.typography.subtitle2.copy(fontSize = 18.sp)
        )
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PostListItemPreview() {
    InstagramTheme {
        Surface(color = MaterialTheme.colors.surface) {
            PostListItem(
                post = samplePosts.first().toDomainPost(),
                onPostClick = {},
                onProfileClick = {},
                onCommentClick = {},
                onLikeClick = {}
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PostHeaderPreview() {
    InstagramTheme {
        Surface(color = MaterialTheme.colors.surface) {
            PostHeader(
                name = "Mr Dip",
                profileUrl = "",
                date = "20 min",
                onProfileClick = {}
            )
        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PostLikesRowPreview() {
    InstagramTheme {
        Surface(color = MaterialTheme.colors.surface) {
            PostLikesRow(
                likesCount = 12,
                commentCount = 2,
                onLikeClick = {},
                onCommentClick = {},
                isPostLiked = true
            )
        }
    }
}