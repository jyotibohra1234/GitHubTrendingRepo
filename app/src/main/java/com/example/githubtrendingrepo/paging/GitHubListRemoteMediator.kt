package com.example.githubtrendingrepo.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.githubtrendingrepo.app.Constants
import com.example.githubtrendingrepo.app.Constants.NETWORK_PAGE_SIZE
import com.example.githubtrendingrepo.rest.api.GithubApiService
import com.example.githubtrendingrepo.room.dao.GithubDao
import com.example.githubtrendingrepo.room.entity.GitHubDataModel
import com.example.githubtrendingrepo.room.entity.RemoteKey
import java.io.IOException
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class GitHubListRemoteMediator @Inject constructor(
    private val dao: GithubDao,
    private val apiService: GithubApiService
) : RemoteMediator<Int, GitHubDataModel>() {

    private var query: String = "android"
    private var order: String = "desc"

    fun setQueryParameter(
        query: String
    ): GitHubListRemoteMediator {
        return this.apply {
            this.query = query
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GitHubDataModel>
    ): MediatorResult {
        return try {
            val nextPageNumber = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    dao.getNextPageNumber(query) ?: 1
                }
            }
            val response = apiService.getGitHubDetails(query = query, page = nextPageNumber,order=order)
            response?.message?.let {
                return MediatorResult.Error(Throwable(it))
            }
            if (loadType == LoadType.REFRESH) {
                dao.deleteAllGitHubData()
                dao.deleteRemoteKey(query)
            }
            response?.items?.let {
                dao.insertGitHub(it)
                dao.insertKey(RemoteKey(query, nextPageNumber + 1))
            }
            MediatorResult.Success(
                endOfPaginationReached = response?.items?.isEmpty() ?: true || (nextPageNumber * NETWORK_PAGE_SIZE >= response?.total_count!!)
            )
        } catch (e: Throwable) {
            MediatorResult.Error(Throwable(if (e is IOException) Constants.NO_INTERNET_AVAILABLE else Constants.SERVER_ERROR))
        }
    }

    override suspend fun initialize(): InitializeAction {
        return if (dao.getNextPageNumber(query) == null) InitializeAction.LAUNCH_INITIAL_REFRESH else InitializeAction.SKIP_INITIAL_REFRESH
    }
}
