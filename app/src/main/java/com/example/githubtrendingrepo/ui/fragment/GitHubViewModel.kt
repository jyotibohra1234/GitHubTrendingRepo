package com.example.githubtrendingrepo.ui.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.githubtrendingrepo.paging.GitHubListRemoteMediator
import com.example.githubtrendingrepo.room.dao.GithubDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GitHubViewModel @Inject constructor(
    private val dao: GithubDao,
    private val remoteMediator: GitHubListRemoteMediator,
) : ViewModel() {

    private var query: String = "android"

    fun setQueryParameter(
        query: String
    ): GitHubViewModel {
        return this.apply {
            this.query = query
        }
    }


    @OptIn(ExperimentalPagingApi::class)
    internal val pagingDataLiveData by lazy {
        Pager(
            config = PagingConfig(pageSize = 10, initialLoadSize = 10),
            remoteMediator = remoteMediator.setQueryParameter(query = query)
        ) {
            dao.getGitHubListData()
        }.flow.asLiveData().cachedIn(viewModelScope)
    }
}
