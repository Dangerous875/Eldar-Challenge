package com.barzabaldevs.eldarwallet.ui.screens.payScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barzabaldevs.eldarwallet.R
import com.barzabaldevs.eldarwallet.ui.components.CircularProgressBar
import com.barzabaldevs.eldarwallet.ui.components.CreditCardDetailItem
import com.barzabaldevs.eldarwallet.ui.components.SetOrientationScreen
import com.barzabaldevs.eldarwallet.ui.components.TopBarGeneric
import com.barzabaldevs.eldarwallet.ui.core.navigation.OrientationScreen
import com.barzabaldevs.eldarwallet.ui.core.theme.Background
import com.barzabaldevs.eldarwallet.ui.core.theme.Primary
import com.barzabaldevs.eldarwallet.ui.core.theme.Secondary
import com.barzabaldevs.eldarwallet.ui.screens.mainScreen.viewmodel.MainScreenViewModel

@Composable
fun PayScreen(viewModel: MainScreenViewModel, navigateToMainScreen: () -> Unit) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val simulatePay by viewModel.simulatePay.collectAsState()
    val paySuccess by viewModel.paySuccess.collectAsState()

    var isBalanceVisible by remember { mutableStateOf(false) }
    var amountToPay by remember { mutableStateOf("") }

    SetOrientationScreen(
        context = context,
        orientation = OrientationScreen.PORTRAIT.orientation,
    )
    when {
        simulatePay -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressBar(message = "Processing Payment")
            }
        }

        paySuccess -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Button(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .width(250.dp)
                        .height(80.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    onClick = {
                        navigateToMainScreen()
                        viewModel.resetPay()
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = Background
                    )
                ) {
                    Text(
                        text = "<- Back to Home",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.iv_paysuccess),
                    contentDescription = null
                )

            }
        }

        else -> {
            Scaffold(topBar = {
                TopBarGeneric(title = "Pay Screen", navBack = { navigateToMainScreen() })
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
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        CreditCardDetailItem(
                            uiState,
                            isVisible = isBalanceVisible,
                            onVisibilityChange = { isBalanceVisible = !isBalanceVisible })

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = amountToPay,
                            onValueChange = { amountToPay = it },
                            singleLine = true,
                            maxLines = 1,
                            label = { Text("Enter Amount") },
                            placeholder = { Text("0.00") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (uiState.userData!!.balance >= amountToPay.toDouble()) {
                                    viewModel.processPayment(amountToPay)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Insufficient Balance",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }

                            },
                            enabled = amountToPay.isNotEmpty(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Background.copy(alpha = 0.7f),
                                contentColor = Secondary
                            )
                        ) {
                            Text(
                                text = "Pay Amount",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

    }
}



