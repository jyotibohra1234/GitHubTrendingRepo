package com.example.githubtrendingrepo.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class GitHubDataModel(
    @PrimaryKey val id: String, val name: String,
    val full_name: String,
    val html_url: String, )
