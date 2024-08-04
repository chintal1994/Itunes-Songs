package com.encora.practical.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.encora.practical.data.database.SongEntity
import com.encora.practical.viewmodel.SongViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSongsListScreen(viewModel: SongViewModel, navController: NavHostController) {
    val songs by viewModel.allSongs.collectAsStateWithLifecycle(emptyList())
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle(false) // Example

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Top Songs",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    )
                },
            )
        },
        content = { padding ->
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (songs.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Loading...", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    items(songs) { song ->
                        SongItem(song) { selectedSong ->
                            Log.d("songID->", "" + selectedSong.title)
                            navController.navigate("song_detail/${selectedSong.title}")
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun SongItem(entry: SongEntity, onClick: (SongEntity) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick.invoke(entry) },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(entry.imageUrl),
                contentDescription = entry.title ?: "Song Image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = entry.title ?: "Unknown Title",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = entry.artist ?: "Unknown Artist",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
