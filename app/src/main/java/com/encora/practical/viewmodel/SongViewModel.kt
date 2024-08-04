package com.encora.practical.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.encora.practical.data.database.SongEntity
import com.encora.practical.data.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class SongViewModel(private val repository: SongRepository) : ViewModel() {

    val allSongs: Flow<List<SongEntity>> = repository.getAllSongs()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        viewModelScope.launch {
            _isLoading.value = true
            repository.refreshSongs()
            _isLoading.value = false
        }
    }

    fun getSongDetails(songId: String?): Flow<SongEntity?> {
        return songId?.let { repository.getSongById(it) } ?: flowOf(null)
    }
}
