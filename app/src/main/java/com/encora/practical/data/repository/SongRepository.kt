package com.encora.practical.data.repository
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.encora.practical.api.ApiService
import com.encora.practical.data.Feed
import com.encora.practical.data.database.SongDao
import com.encora.practical.data.database.SongEntity
import com.encora.practical.utils.formatReleaseDate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class SongRepository(private val songDao: SongDao, private val itunesService: ApiService) {

    fun refreshSongs() {
        itunesService.getTopSongs().enqueue(object : Callback<Feed> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<Feed>, response: Response<Feed>) {
                if (response.isSuccessful) {


                    response.body()?.entries?.let { songs ->

                        val songEntities = songs.map { song ->

                            Log.d("release_date->",song.releaseDate.toString())
                            Log.d("release_date->",song.rights.toString())

                            SongEntity(
                                songId = song.title ?: "",
                                songUrl = song.id ?: "",
                                title = song.title ?: "",
                                artist = song.artist ?: "",
                                imageUrl = song.images?.firstOrNull()?.url ?: "",
                                link =  song.previewLinks?.getOrNull(1)?.href ?: "",
                                release_date = formatReleaseDate(song.releaseDate) ?: "",
                                rights = song.rights ?: ""
                            )
                        }
                        // Insert into the database
                        GlobalScope.launch {
                            songDao.insertAll(songEntities)
                        }
                    }
                } else {
                    response.errorBody()
                    // Handle response error
                }
            }

            override fun onFailure(call: Call<Feed>, t: Throwable) {
                // Handle request failure
                t.printStackTrace()
            }
        })
    }

    fun getAllSongs(): Flow<List<SongEntity>> = songDao.getAllSongs()
    fun getSongById(songId: String?): Flow<SongEntity?> {
        return songDao.getSongById(songId)
    }
}
