package com.example.githubtrendingrepo.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKey(@PrimaryKey val query: String, val nextKey: Int) {
}