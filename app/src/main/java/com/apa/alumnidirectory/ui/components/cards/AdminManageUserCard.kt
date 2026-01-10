package com.apa.alumnidirectory.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.ui.components.pfp.DefaultPfp
import com.apa.alumnidirectory.ui.components.pfp.Pfp1
import com.apa.alumnidirectory.ui.components.pfp.Pfp2
import com.apa.alumnidirectory.ui.theme.SecondaryG
import com.apa.alumnidirectory.ui.theme.Text1

@Composable
fun AdminManageUserCard(
    user: UserData,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                SecondaryG, RoundedCornerShape(12.dp)
            )
            .padding(6.dp)
            .clickable { onClick(user.uid) },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = Text1
        ),
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize(0.2f)
                    .aspectRatio(1f)
                    .background(Color.Gray, RoundedCornerShape(12.dp))
            ) {
                when (user.photoUrl) {
                    1 -> Pfp1()
                    2 -> Pfp2()
                    else -> DefaultPfp()
                }
            }
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
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        Icons.Filled.Edit,
                        "",
                        modifier = Modifier.size(30.dp)
                    )
                }
                Text(
                    "Graduation Year: ${user.graduationYear}"
                )
            }
        }
    }
}