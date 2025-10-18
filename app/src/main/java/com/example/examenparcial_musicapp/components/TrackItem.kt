package com.example.examenparcial_musicapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun TrackItem(image: String, title: String, artist: String) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E9FF)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            AsyncImage(
                model = image ?: "",
                contentDescription = title,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column (modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.SemiBold, color = Color(0xFF2F153E))
                Text(artist, color = Color(0xFF2F153E).copy(alpha = 0.7f))
            }
            IconButton(onClick = { /* opciones */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Options",
                    tint = Color(0xFF2F153E)
                )
            }
        }
    }
}