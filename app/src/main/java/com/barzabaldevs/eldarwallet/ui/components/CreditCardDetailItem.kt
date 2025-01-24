package com.barzabaldevs.eldarwallet.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barzabaldevs.eldarwallet.ui.components.utils.formatCardNumber
import com.barzabaldevs.eldarwallet.ui.components.utils.formatDate
import com.barzabaldevs.eldarwallet.ui.components.utils.getCardIcon
import com.barzabaldevs.eldarwallet.ui.core.theme.Secondary
import com.barzabaldevs.eldarwallet.ui.screens.mainScreen.viewmodel.MainScreenState

@Composable
fun CreditCardDetailItem(
    uiState: MainScreenState,
    isVisible: Boolean,
    onVisibilityChange: () -> Unit
) {
    val currentCard = uiState.cardSelected!!
    val currentUser = uiState.userData!!
    var showFullNumber by remember { mutableStateOf(isVisible) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = Secondary),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = if (showFullNumber) formatCardNumber(currentCard.cardNumber)
                        else currentCard.cardNumber.replaceRange(0, 12, "**** **** **** "),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 20.sp,
                        color = Secondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${currentUser.name} ${currentUser.lastName}",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 16.sp,
                        color = Secondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (showFullNumber) {
                            "$${uiState.userData.balance}"
                        } else {
                            "*".repeat(uiState.userData.balance.toString().length)
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        color = Secondary
                    )
                }
                Image(
                    painter = painterResource(id = getCardIcon(currentCard.cardNumber)),
                    contentDescription = "Card Logo",
                    modifier = Modifier.size(50.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Exp: ${formatDate(currentCard.expirationDate)}",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 14.sp,
                    color = Secondary
                )
                IconButton(
                    onClick = {
                        showFullNumber = !showFullNumber
                        onVisibilityChange()
                    },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (showFullNumber) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null,
                        tint = Secondary
                    )
                }
            }
        }
    }
}