package com.barzabaldevs.eldarwallet.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barzabaldevs.eldarwallet.domain.model.CreditCard
import com.barzabaldevs.eldarwallet.ui.components.utils.getCardIcon
import com.barzabaldevs.eldarwallet.ui.components.utils.getCardName
import com.barzabaldevs.eldarwallet.ui.core.theme.Secondary

@Composable
fun CreditCardItem(creditCard: CreditCard, onClick: (CreditCard) -> Unit) {
    Card(
        onClick = { onClick(creditCard) },
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = Secondary),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Column {
                Text(
                    text = creditCard.cardNumber.replaceRange(0, 12, "**** **** **** "),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 24.sp,
                    color = Secondary
                )
                Text(
                    text = getCardName(creditCard.cardNumber),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp,
                    color = Secondary
                )
            }
            Image(
                painter = painterResource(id = getCardIcon(creditCard.cardNumber)),
                contentDescription = "Card Icon",
                modifier = Modifier.size(50.dp)
            )
        }
    }
}