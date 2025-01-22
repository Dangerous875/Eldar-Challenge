package com.barzabaldevs.eldarwallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.barzabaldevs.eldarwallet.ui.core.NavigationWrapper
import com.barzabaldevs.eldarwallet.ui.core.theme.EldarWalletTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EldarWalletTheme(darkTheme = true) {
                NavigationWrapper()
            }
        }
    }
}
