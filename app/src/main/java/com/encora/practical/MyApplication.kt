package com.encora.practical

import RetrofitClient
import android.app.Application
import com.encora.practical.data.database.AppDatabase
import com.encora.practical.data.repository.SongRepository

class MyApplication : Application() {
    val repository: SongRepository by lazy {
        val database = AppDatabase.getDatabase(this)
        val songDao = database.songDao()
        val itunesService = RetrofitClient.itunesService
        SongRepository(songDao, itunesService)
    }
}
