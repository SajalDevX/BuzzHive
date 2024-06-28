package com.example.instagram.android.common.util

import com.example.instagram.common.util.Result
import kotlinx.coroutines.delay


interface PagingManager<Model>{
    suspend fun loadItems()
    fun reset()
}

class DefaultPagingManager<Model>(
    private inline val onRequest: suspend (page: Int) -> Result<List<Model>>,
    private inline val onSuccess: (items: List<Model>, page: Int) -> Unit,
    private inline val onError: (cause: String, page: Int) -> Unit,
    private inline val onLoadStateChange: (isLoading: Boolean) -> Unit
): PagingManager<Model>{

    private var currentPage = Constants.INITIAL_PAGE_NUMBER
    private var isLoading = false

    override suspend fun loadItems() {
        if (isLoading) return
        isLoading = true
        onLoadStateChange(true)
        delay(1_000)

        val result = onRequest(currentPage)
        isLoading = false
        onLoadStateChange(false)

        when(result){
            is Result.Error -> {
                onError(result.message ?: Constants.UNEXPECTED_ERROR_MESSAGE, currentPage)
            }
            is Result.Success -> {
                onSuccess(result.data!!, currentPage)
                currentPage += 1
            }
        }
    }

    override fun reset() {
        currentPage = Constants.INITIAL_PAGE_NUMBER
    }
}
