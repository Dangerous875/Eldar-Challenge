package com.barzabaldevs.eldarwallet.ui.screens.mainScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen(auth: FirebaseAuth, navigateToHomeScreen: () -> Unit) {
Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
    Column {
        Text(text = "Main Screen()")
        Button(onClick = {
            auth.signOut()
            navigateToHomeScreen()
        }) {
            Text("LogOut")
        }
    }

}
}