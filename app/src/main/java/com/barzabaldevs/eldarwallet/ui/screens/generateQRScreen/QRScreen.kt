package com.barzabaldevs.eldarwallet.ui.screens.generateQRScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.barzabaldevs.eldarwallet.ui.components.CircularProgressBar
import com.barzabaldevs.eldarwallet.ui.components.SetOrientationScreen
import com.barzabaldevs.eldarwallet.ui.components.TopBarGeneric
import com.barzabaldevs.eldarwallet.ui.core.navigation.OrientationScreen
import com.barzabaldevs.eldarwallet.ui.core.theme.Background
import com.barzabaldevs.eldarwallet.ui.core.theme.Primary
import com.barzabaldevs.eldarwallet.ui.screens.generateQRScreen.viewmodel.QRScreenViewModel

@Composable
fun QRScreen(viewModel: QRScreenViewModel = hiltViewModel(), navigateToMainScreen: () -> Unit) {
    val isLoading by viewModel.isLoading.collectAsState()
    val qrCodeBitmap by viewModel.qrCodeBitmap.collectAsState()
    val context = LocalContext.current

    SetOrientationScreen(
        context = context,
        orientation = OrientationScreen.PORTRAIT.orientation,
    )

    Scaffold(topBar = {
        TopBarGeneric(title = "QR Generate Pay", navBack = { navigateToMainScreen() })
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    Brush.verticalGradient(
                        listOf(Background, Primary),
                        startY = 0f,
                        endY = 600f,
                    ),
                )
        ) {
            if (isLoading) {
                CircularProgressBar("Loading QR Code...")
            } else {
                qrCodeBitmap?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "QR Code",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

    }
}