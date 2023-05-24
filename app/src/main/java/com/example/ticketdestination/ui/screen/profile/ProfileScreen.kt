package com.example.ticketdestination.ui.screen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ticketdestination.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    AboutContent()
}

@Composable
fun AboutContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        AsyncImage(
            model = "https://firebasestorage.googleapis.com/v0/b/bangkit-dashboard/o/production%2F2023-B1%2Fprofiles%2F59d91af9-e5fd-4fc2-a464-c90c3b122285.png?alt=media&token=2898da6e-2921-47e9-8dcc-f7de3443f0e7" ,
            contentDescription = null,
            modifier = Modifier
                .padding(4.dp)
                .clip(CircleShape)
                .size(200.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.name),
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.email),
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}