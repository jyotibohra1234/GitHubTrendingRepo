package com.example.githubtrendingrepo.rest.response

object ResponseView {
    data class BaseResponse<T>(
        val Response: String,
        val total_count: Long,
        val items: T?,
        val message: String?,
        val incomplete_results: String
    )
}
