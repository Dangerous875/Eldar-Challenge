package com.barzabaldevs.eldarwallet.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import com.barzabaldevs.eldarwallet.ui.core.theme.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarGeneric(title: String, navBack: () -> Unit) {
    TopAppBar(
        title = { Text(text = title, color = Secondary, fontStyle = FontStyle.Italic) },
        navigationIcon = {
            IconButton(onClick = { navBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Black)
    )
}