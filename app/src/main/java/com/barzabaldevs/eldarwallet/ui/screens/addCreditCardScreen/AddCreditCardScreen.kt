package com.barzabaldevs.eldarwallet.ui.screens.addCreditCardScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barzabaldevs.eldarwallet.domain.model.UserModel
import com.barzabaldevs.eldarwallet.ui.components.PreviewNewCard
import com.barzabaldevs.eldarwallet.ui.components.SetOrientationScreen
import com.barzabaldevs.eldarwallet.ui.components.TopBarGeneric
import com.barzabaldevs.eldarwallet.ui.core.navigation.OrientationScreen
import com.barzabaldevs.eldarwallet.ui.core.theme.Background
import com.barzabaldevs.eldarwallet.ui.core.theme.Primary
import com.barzabaldevs.eldarwallet.ui.core.theme.Secondary
import com.barzabaldevs.eldarwallet.ui.screens.mainScreen.viewmodel.MainScreenViewModel
import java.util.Locale

@Composable
fun AddCreditCardScreen(viewModel: MainScreenViewModel,navigateToMainScreen: () -> Unit) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()

    SetOrientationScreen(
        context = context,
        orientation = OrientationScreen.PORTRAIT.orientation,
    )
    var cardNumber by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    SetOrientationScreen(
        context = context,
        orientation = OrientationScreen.PORTRAIT.orientation,
    )

    Scaffold(topBar = {
        TopBarGeneric(title = "Add New Card", navBack = { navigateToMainScreen()})
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    Brush.verticalGradient(
                        listOf(Background, Primary),
                        startY = 0f,
                        endY = 600f
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                PreviewNewCard(firstName, lastName, cardNumber, expirationDate, cvv)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    OutlinedTextField(
                        value = cardNumber,
                        onValueChange = { cardNumber = it.take(16) }, // Limita a 16 caracteres
                        label = { Text("Card Number") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Last Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedTextField(
                            value = expirationDate,
                            onValueChange = { expirationDate = it.take(5) },
                            label = { Text("Expiration Date (MM/YY)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        OutlinedTextField(
                            value = cvv,
                            onValueChange = { cvv = it.take(3) },
                            label = { Text("CVV") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            maxLines = 1
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    onClick = {
                        if (cardNumber.length == 16 && firstName.isNotBlank() && lastName.isNotBlank() && expirationDate.isNotBlank() && cvv.length == 3) {
                            if (validName(firstName, lastName, uiState.value.userData!!)) {
                                viewModel.addCreditCard(
                                    cardNumber = cardNumber,
                                    securityCode = cvv,
                                    expirationDate = expirationDate,
                                    name = firstName,
                                    lastName = lastName
                                )
                                navigateToMainScreen()
                            } else {
                                Toast.makeText(
                                    context,
                                    "First name and last name don't match",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } else {
                            Toast.makeText(
                                context,
                                "Complete all fields correctly",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Background.copy(
                            alpha = 0.7f
                        )
                    ),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Plus Icon",
                            modifier = Modifier.size(32.dp),
                            tint = Secondary
                        )
                        Text(
                            text = "Add a new card",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Secondary
                        )
                    }

                }
            }
        }
    }
}

fun validName(firstName: String, lastName: String, userData: UserModel): Boolean {
    val nameRegister = userData.name.trim().uppercase(Locale.getDefault())
    val lastNameRegister = userData.lastName.trim().uppercase(Locale.getDefault())

    return firstName.trim().uppercase(Locale.getDefault()) == nameRegister &&
            lastName.trim().uppercase(Locale.getDefault()) == lastNameRegister
}