package com.barzabaldevs.eldarwallet.ui.screens.mainScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.barzabaldevs.eldarwallet.ui.screens.mainScreen.viewmodel.MainScreenViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen(
    auth: FirebaseAuth,
    navigateToHomeScreen: () -> Unit,
    viewModel: MainScreenViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    if (uiState.isLoading){
        CircularProgressIndicator()
    }else{
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            val currentUser = uiState.userData!!
            Column {
                Text(text = "Hello ${currentUser.name}, ${currentUser.lastName}")
                Button(onClick = {
                    auth.signOut()
                    navigateToHomeScreen()
                }) {
                    Text("LogOut")
                }
            }

        }
    }

}