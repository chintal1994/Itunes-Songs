package com.encora.practical.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey val songId: String,
    val songUrl: String,
    val title: String,
    val artist: String,
    val imageUrl: String,
    val link: String,
    val release_date: String,
    val rights: String,
)