package com.barzabaldevs.eldarwallet.ui.screens.mainScreen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barzabaldevs.eldarwallet.R
import com.barzabaldevs.eldarwallet.domain.model.CreditCard
import com.barzabaldevs.eldarwallet.ui.components.ButtonAddCard
import com.barzabaldevs.eldarwallet.ui.components.CircularProgressBar
import com.barzabaldevs.eldarwallet.ui.components.CreditCardItem
import com.barzabaldevs.eldarwallet.ui.components.ExitConfirmation
import com.barzabaldevs.eldarwallet.ui.components.SetOrientationScreen
import com.barzabaldevs.eldarwallet.ui.components.ShowBalance
import com.barzabaldevs.eldarwallet.ui.core.navigation.OrientationScreen
import com.barzabaldevs.eldarwallet.ui.core.theme.Background
import com.barzabaldevs.eldarwallet.ui.core.theme.Primary
import com.barzabaldevs.eldarwallet.ui.core.theme.Secondary
import com.barzabaldevs.eldarwallet.ui.screens.mainScreen.viewmodel.MainScreenState
import com.barzabaldevs.eldarwallet.ui.screens.mainScreen.viewmodel.MainScreenViewModel
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("ContextCastToActivity")
@Composable
fun MainScreen(
    auth: FirebaseAuth,
    navigateToHomeScreen: () -> Unit,
    navigateToQRScreen: () -> Unit,
    navigateToPayScreen: () -> Unit,
    navigateToAddCreditCard: () -> Unit,
    viewModel: MainScreenViewModel
) {
    val context = LocalContext.current

    SetOrientationScreen(
        context = context,
        orientation = OrientationScreen.PORTRAIT.orientation,
    )

    LaunchedEffect(key1 = true) {
        viewModel.initUserData()
    }

    val uiState by viewModel.uiState.collectAsState()
    val activity = LocalContext.current as Activity

    var showExitConfirmation by rememberSaveable {
        mutableStateOf(false)
    }
    var showUnLogConfirmation by rememberSaveable {
        mutableStateOf(false)
    }

    ExitConfirmation(
        show = showExitConfirmation,
        onDismiss = { showExitConfirmation = false },
        onConfirm = { activity.finishAffinity() },
        title = stringResource(id = R.string.ExitConfirmation),
        message = stringResource(id = R.string.ExitApp),
    )

    ExitConfirmation(
        show = showUnLogConfirmation,
        onDismiss = { showUnLogConfirmation = false },
        onConfirm = {
            auth.signOut()
            navigateToHomeScreen()
        },
        title = "WARNING",
        message = "Are you sure you want to unlog?",
    )


    if (uiState.isLoading) {
        CircularProgressBar("Loading")
    } else {
        Scaffold(
            topBar = {
                TopBar(
                    title = "Welcome ${uiState.userData?.name} ${uiState.userData?.lastName}",
                    showDialogDesLogin = { showUnLogConfirmation = true },
                    showDialogExitApp = { showExitConfirmation = true },
                    navigateToQRScreen = { navigateToQRScreen() }
                )
            }
        ) { paddingValues ->
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
                if (uiState.creditCards.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        ShowBalance(uiState)
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 16.dp),
                            text = "No cards available. Add a new one!",
                            textAlign = TextAlign.Center
                        )
                        ButtonAddCard { navigateToAddCreditCard() }
                    }
                } else {
                    ContentViewCards(
                        uiState = uiState,
                        navigateToPayScreen = { creditCard ->
                            viewModel.setSelectedCard(creditCard)
                            navigateToPayScreen()
                        },
                        navigateToAddCreditCard = { navigateToAddCreditCard() }
                    )
                }
            }
        }
    }

    BackHandler {
        showExitConfirmation = true
    }
}

@Composable
fun ContentViewCards(
    uiState: MainScreenState,
    navigateToPayScreen: (CreditCard) -> Unit,
    navigateToAddCreditCard: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ShowBalance(uiState)
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.creditCards) { creditCard ->
                CreditCardItem(
                    creditCard = creditCard,
                    onClick = { navigateToPayScreen(creditCard) })
            }
        }
        ButtonAddCard { navigateToAddCreditCard() }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    showDialogDesLogin: () -> Unit,
    showDialogExitApp: () -> Unit,
    navigateToQRScreen: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title, color = Secondary, fontStyle = FontStyle.Italic) },
        colors = TopAppBarDefaults.topAppBarColors(Color.Black),
        actions = {
            IconButton(onClick = { navigateToQRScreen() }) {
                Icon(painter = painterResource(id = R.drawable.ic_qr), contentDescription = null)
            }
            DropdownMenuButton(showDialogDesLogin, showDialogExitApp)
        }
    )
}

@Composable
fun DropdownMenuButton(showDialogDesLogin: () -> Unit, showDialogExitApp: () -> Unit) {

    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, tint = Color.White)
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(
            modifier = Modifier.border(1.dp, Secondary),
            onClick = { showDialogDesLogin() },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = "Logout"
                    )
                    Text(
                        text = "Logout",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

            }
        )

        DropdownMenuItem(
            modifier = Modifier.border(1.dp, Secondary),
            onClick = { showDialogExitApp() },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Logout"
                    )
                    Text(
                        text = "Exit App",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

            }
        )
    }
}
