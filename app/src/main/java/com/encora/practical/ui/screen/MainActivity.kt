@file:OptIn(ExperimentalMaterial3Api::class)

package com.encora.practical.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.encora.practical.MyApplication
import com.encora.practical.data.repository.SongRepository
import com.encora.practical.ui.theme.EncoraPracticalTheme
import com.encora.practical.viewmodel.SongViewModel
import com.encora.practical.viewmodel.SongViewModelFactory

class MainActivity : ComponentActivity() {

    private val repository: SongRepository by lazy {
        (application as MyApplication).repository
    }

    private val viewModel: SongViewModel by viewModels {
        SongViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EncoraPracticalTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    mainApp(viewModel)
                }
            }
        }
    }
}


@Composable
fun mainApp(viewModel: SongViewModel) {

    var navController = rememberNavController()

    NavHost(navController, startDestination = "song_list") {
        composable("song_list") {
            TopSongsListScreen(viewModel, navController)
        }

        composable("song_detail/{songId}") { backStackEntry ->
            val songId = backStackEntry.arguments?.getString("songId")
            SongDetailScreen(viewModel, songId, navController)
        }
    }
}






