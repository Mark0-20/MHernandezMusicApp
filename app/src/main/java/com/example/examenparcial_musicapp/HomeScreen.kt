package com.example.examenparcial_musicapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.examenparcial_musicapp.components.AlbumCardLarge
import com.example.examenparcial_musicapp.components.MiniPlayer
import com.example.examenparcial_musicapp.components.SmallRecentlyPlayedItem
import com.example.examenparcial_musicapp.data.RetrofitClient
import com.example.examenparcial_musicapp.model.Album
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun HomeScreen(
    onAlbumClick: (Album) -> Unit,
    modifier: Modifier = Modifier
) {
    val albumsState = remember { mutableStateListOf<Album>() }
    var isLoading by remember { mutableStateOf(true) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    var currentAlbumId by remember { mutableStateOf<String?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            isLoading = true
            val result = withContext(Dispatchers.IO) { RetrofitClient.api.getAlbums() }
            albumsState.clear()
            albumsState.addAll(result)
            errorMsg = null
        } catch (e: Exception) {
            Log.e("HomeScreen", "fetch error", e)
            errorMsg = e.localizedMessage ?: "Error fetching albums"
        } finally {
            isLoading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3E9FF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 88.dp)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(140.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(Color(0xFF7C4DFF), Color(0xFF9C6BFF))
                        )
                    )
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Menu, contentDescription = "menu", tint = Color.White)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Good Morning!",
                            color = Color.White.copy(alpha = 0.9f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Marco Hernandez",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.Search, contentDescription = "search", tint = Color.White)
                }
            }

            // Albums Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Albums",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text("See more", color = Color(0xFF7C4DFF))
            }

            Spacer(modifier = Modifier.height(8.dp))

            when {
                isLoading -> Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                errorMsg != null -> Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: $errorMsg", color = Color.Red)
                }

                else -> LazyRow(
                    modifier = Modifier.padding(start = 16.dp),
                    contentPadding = PaddingValues(end = 16.dp)
                ) {
                    items(albumsState) { album ->
                        AlbumCardLarge(
                            album = album,
                            onClick = { onAlbumClick(album) }, // ✅ pasamos el objeto Album
                            onPlayClick = {
                                if (currentAlbumId == album.albumId) isPlaying = !isPlaying
                                else {
                                    currentAlbumId = album.albumId
                                    isPlaying = true
                                }
                            }
                        )
                    }
                }
            }

            // Recently Played
            Spacer(modifier = Modifier.height(18.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Recently Played",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text("See more", color = Color(0xFF7C4DFF))
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(albumsState) { album ->
                    SmallRecentlyPlayedItem(
                        album = album,
                        onClick = { onAlbumClick(album) }, // ✅ igual aquí
                        onPlayClick = {
                            if (currentAlbumId == album.albumId) isPlaying = !isPlaying
                            else {
                                currentAlbumId = album.albumId
                                isPlaying = true
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        // Mini Player
        val current = albumsState.firstOrNull { it.albumId == currentAlbumId } ?: albumsState.firstOrNull()
        MiniPlayer(
            album = current,
            isPlaying = isPlaying,
            onPlayPause = { isPlaying = !isPlaying },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp)
        )
    }
}

