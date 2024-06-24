package com.example.instagram.android.account.follows

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dipumba.ytsocialapp.android.account.follows.FollowsListItem

@Composable
fun FollowsScreen(
    modifier: Modifier = Modifier,
    uiState: FollowsUiState,
    fetchFollows: () -> Unit,
    onItemClick: (Int) -> Unit
) {


    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ){
            items(items = uiState.sampleFollowsUsers, key = { user -> user.id}){
                FollowsListItem(name = it.name, bio = it.bio, imageUrl = it.profileUrl) {
                    onItemClick(it.id)
                }
            }
        }

        if (uiState.isLoading && uiState.sampleFollowsUsers.isEmpty()){
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(key1 = Unit, block = { fetchFollows() })
}














