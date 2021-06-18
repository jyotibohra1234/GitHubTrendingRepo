//package com.example.githubtrendingrepo.work
//
//import android.content.Context
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import retrofit2.HttpException
//
//class FetchDataWorker(appContext: Context, params: WorkerParameters):
//    CoroutineWorker(appContext, params)
//    {
//
//        companion object {
//        const val WORK_NAME = "FetchDataWorker"
//    }
//
//        /**
//         * A coroutine-friendly method to do your work.
//         */
//        override suspend fun doWork(): Result {
//            val database = getGitHubDao(applicationContext)
//            val repository =
//                ArticlesRepository(database)
//            return try {
//                repository.refreshArticles()
//                Result.success()
//            } catch (e: HttpException) {
//                Result.retry()
//            }
//        }
//    }