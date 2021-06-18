package com.example.githubtrendingrepo.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubtrendingrepo.room.dao.GithubDao
import com.example.githubtrendingrepo.room.entity.GitHubDataModel
import com.example.githubtrendingrepo.room.entity.RemoteKey

@Database(entities = [GitHubDataModel::class, RemoteKey::class], version = 2, exportSchema = false)
abstract class GitHubDatabase:RoomDatabase() {
    abstract fun getGithubRepoDao(): GithubDao

}