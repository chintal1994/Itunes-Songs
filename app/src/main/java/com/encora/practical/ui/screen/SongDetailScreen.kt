@file:OptIn(ExperimentalMaterial3Api::class)

package com.encora.practical.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.encora.practical.data.database.SongEntity
import com.encora.practical.viewmodel.SongViewModel

@Composable
fun SongDetailScreen(viewModel: SongViewModel, songId: String?, navController: NavController) {
    val song by viewModel.getSongDetails(songId).collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Song Details",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        },
        content = { paddingValues ->
            song?.let {
                SongDetailContent(
                    song = it,
                    modifier = Modifier.padding(paddingValues)
                )
            } ?: run {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    //CircularProgressIndicator()
                }
            }
        }
    )
}

@Composable
fun SongDetailContent(song: SongEntity, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        ) {
            Image(
                painter = rememberImagePainter(song.imageUrl),
                contentDescription = "Song cover image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = song.title,
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = song.artist,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal)
                )

            }
        }

        PlayButton(previewUrl = song.link)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = song.release_date,
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = song.rights ?: "No additional details available.",
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal)
        )
    }
}

@Composable
fun PlayButton(previewUrl: String) {
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    var isPlaying by remember { mutableStateOf(false) } // State to track playback

    LaunchedEffect(previewUrl) {
        val mediaItem = MediaItem.fromUri(previewUrl)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }

    LaunchedEffect(isPlaying) {
        exoPlayer.playWhenReady = isPlaying
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Button(
        onClick = {
            isPlaying = !isPlaying
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red // Background color of the button
        ),
        shape = RoundedCornerShape(10.dp), // Rounded corners
        modifier = Modifier
            .fillMaxWidth() // Button takes up full width
            .padding(8.dp) // Add padding around the button
    ) {
        Text(
            text = if (isPlaying) "Pause Song" else "Play Song",
            color = Color.White, // Text color
            textAlign = TextAlign.Center, // Center the text
            modifier = Modifier
                .fillMaxWidth() // Text takes up full width of the button
                .padding(8.dp) // Add padding inside the button
        )
    }
}
