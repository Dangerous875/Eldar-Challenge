package com.barzabaldevs.eldarwallet.ui.screens.addCreditCardScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.barzabaldevs.eldarwallet.ui.components.SetOrientationScreen
import com.barzabaldevs.eldarwallet.ui.core.navigation.OrientationScreen
import com.barzabaldevs.eldarwallet.ui.screens.mainScreen.viewmodel.MainScreenViewModel

@Composable
fun AddCreditCardScreen(viewModel: MainScreenViewModel) {
    val context = LocalContext.current

    SetOrientationScreen(
        context = context,
        orientation = OrientationScreen.PORTRAIT.orientation,
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "AddCreditCardScreen")
    }
}