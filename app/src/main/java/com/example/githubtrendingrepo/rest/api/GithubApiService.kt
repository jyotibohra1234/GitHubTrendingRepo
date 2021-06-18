package com.example.githubtrendingrepo.rest.api

import com.example.githubtrendingrepo.rest.response.ResponseView
import com.example.githubtrendingrepo.room.entity.GitHubDataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/repositories")
    suspend fun getGitHubDetails(
        @Query("order") order: String,
        @Query("q") query: String,
        @Query("page") page: Int
    ): ResponseView.BaseResponse<List<GitHubDataModel>>
}
