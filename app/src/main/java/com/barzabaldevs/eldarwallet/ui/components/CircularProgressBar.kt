package com.barzabaldevs.eldarwallet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.barzabaldevs.eldarwallet.R
import com.barzabaldevs.eldarwallet.ui.core.theme.Background
import com.barzabaldevs.eldarwallet.ui.core.theme.Primary
import com.barzabaldevs.eldarwallet.ui.core.theme.Secondary
import kotlinx.coroutines.delay

@Composable
fun CircularProgressBar(message : String) {
    var dotCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            dotCount = (dotCount + 1) % 4
            delay(500L)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                listOf(Background, Primary),
                startY = 0f,
                endY = 600f,
            )),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        AsyncImage(
            modifier = Modifier
                .size(250.dp),
            model = R.drawable.eldar_title,
            contentDescription = "Logo",
            contentScale = ContentScale.Inside
        )

        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = Secondary,
            strokeWidth = 6.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message + ".".repeat(dotCount),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            color = Secondary
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "Please wait",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            ),
            color = Secondary
        )
    }
}