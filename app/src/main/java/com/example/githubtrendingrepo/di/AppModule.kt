package com.example.githubtrendingrepo.di


import android.content.Context
import androidx.room.Room
import com.example.githubtrendingrepo.BuildConfig
import com.example.githubtrendingrepo.app.Constants
import com.example.githubtrendingrepo.rest.api.GithubApiService
import com.example.githubtrendingrepo.room.database.GitHubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, GitHubDatabase::class.java, "gitHub_database").build()

    @Provides
    @Singleton
    fun getGitHubDao(database: GitHubDatabase) = database.getGithubRepoDao()

    @Provides
    @Singleton
    fun getOkhttpClient(): OkHttpClient = OkHttpClient.Builder().run {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            if (BuildConfig.BUILD_TYPE == Constants.DEBUG_TYPE) level =
                HttpLoggingInterceptor.Level.BODY
        }
        addNetworkInterceptor(loggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun getGitHubDetailService(okHttpClient: OkHttpClient): GithubApiService =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(GithubApiService::class.java)
}