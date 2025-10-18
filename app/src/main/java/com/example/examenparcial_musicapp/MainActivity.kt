package com.example.examenparcial_musicapp

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.examenparcial_musicapp.model.Album
import com.example.examenparcial_musicapp.ui.theme.ExamenParcialMusicAppTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamenParcialMusicAppTheme {
                MusicAppNavigation()
            }
        }
    }
}

@Composable
fun MusicAppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Pantalla principal
        composable("home") {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                HomeScreen(
                    onAlbumClick = { album ->
                        // Serializamos y codificamos el álbum para pasar por la ruta
                        val albumJson = Uri.encode(Gson().toJson(album))
                        navController.navigate("detail/$albumJson")
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }

        // Pantalla de detalle
        composable(
            route = "detail/{albumJson}",
            arguments = listOf(navArgument("albumJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val albumJson = backStackEntry.arguments?.getString("albumJson")?.let { Uri.decode(it) }
            val album = Gson().fromJson(albumJson, Album::class.java)

            DetailScreen(
                album = album,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ExamenParcialMusicAppTheme {
        HomeScreen(onAlbumClick = {})
    }
}