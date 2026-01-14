package com.apa.alumnidirectory.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.ui.theme.Danger
import com.apa.alumnidirectory.ui.theme.Primary
import com.apa.alumnidirectory.ui.theme.SecondaryG
import com.apa.alumnidirectory.ui.theme.Text1

@Composable
fun AdminPendingUserCard(
    user: UserData,
    onApproved: () -> Unit,
    onRejected: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                SecondaryG, RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = Text1
        ),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        user.fullName,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        user.graduationYear,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.height(12.dp))
                Text("Email: ${user.email}")
                Text("Location: ${user.location.state}, ${user.location.country}")
                Spacer(Modifier.height(12.dp))
                Text("Job Title: ${user.position}")
                Text("Company: ${user.company}")
                Text("Stack: ${user.primaryStack}")
            }
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { onApproved() },
                    modifier = Modifier
                        .width(130.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Approve",
                        modifier = Modifier.padding(0.dp, 6.dp)
                    )
                }
                Button(
                    onClick = { onRejected() },
                    modifier = Modifier
                        .width(130.dp)
                        .padding(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Danger
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Reject",
                        modifier = Modifier.padding(0.dp, 6.dp)
                    )
                }
            }
        }
    }
}