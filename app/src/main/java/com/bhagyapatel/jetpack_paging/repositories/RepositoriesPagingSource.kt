package com.bhagyapatel.jetpack_paging.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bhagyapatel.jetpack_paging.repositories.data.DI.DependancyContainer
import com.bhagyapatel.jetpack_paging.repositories.data.Remote.RepositoriesApiService
import com.bhagyapatel.jetpack_paging.repositories.data.Remote.Repository

class RepositoriesPagingSource (
    private val repoInterface : RepositoriesApiService
    = DependancyContainer.repositoriesRetrofitClient
) : PagingSource<Int, Repository>() {

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        try {
            val nextPage = params.key ?: 1
            val repos = repoInterface.getRepositories(nextPage).repos

            return LoadResult.Page(
                data = repos,
                prevKey = if(nextPage == 1) null else nextPage-1,
                nextKey = nextPage+1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


}