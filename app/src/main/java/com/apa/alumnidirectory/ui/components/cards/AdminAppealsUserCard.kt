package com.apa.alumnidirectory.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Expand
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apa.alumnidirectory.data.model.request.AppealReq
import com.apa.alumnidirectory.ui.theme.SecondaryG
import com.apa.alumnidirectory.ui.theme.Text1

@Composable
fun AdminAppealsUserCard(
    user: AppealReq,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                SecondaryG, RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
            .clickable { onClick(user.uid) },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = Text1
        ),
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    user.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    user.email,
                    fontSize = 18.sp,
                )
            }
            Icon(
                Icons.Filled.Expand,
                "",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}