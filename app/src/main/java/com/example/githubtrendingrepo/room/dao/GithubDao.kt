package com.example.githubtrendingrepo.room.dao

import androidx.room.*
import com.example.githubtrendingrepo.room.entity.GitHubDataModel
import com.example.githubtrendingrepo.room.entity.RemoteKey
import androidx.paging.PagingSource

@Dao
interface GithubDao {
    @Query("Select * from GitHubDataModel")
    fun getGitHubListData(): PagingSource<Int, GitHubDataModel>

    @Query("Delete from GitHubDataModel")
    suspend fun deleteAllGitHubData()

    @Query("Delete from RemoteKey where `query`=:query")
    suspend fun deleteRemoteKey(query: String)

    @Query("Select nextKey from RemoteKey where `query` = :query")
    suspend fun getNextPageNumber(query: String): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGitHub(moviesDataModel: List<GitHubDataModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(remoteKey: RemoteKey)
}