package com.apa.alumnidirectory.ui.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import com.apa.alumnidirectory.ui.theme.SecondaryG
import com.apa.alumnidirectory.ui.theme.Text1

@Composable
fun HomeUserCard(
    data: UserData,
    onClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (data.role == "admin") Color.Red
                else Color.Transparent,
                RoundedCornerShape(12.dp)
            )
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
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.3f),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .background(Color.Gray, RoundedCornerShape(12.dp))
                    ) {
                        //Temp Pfp Image
                        Icon(
                            Icons.Filled.Person, "",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Text(
                        data.graduationYear,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        Text(
                            data.fullName,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(0.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            data.position,
                            fontSize = 15.sp,
                            lineHeight = 15.sp,
                            maxLines = 1
                        )
                        Text(
                            data.company,
                            fontSize = 15.sp,
                            lineHeight = 15.sp,
                            maxLines = 1
                        )
                        Text(
                            data.primaryStack,
                            fontSize = 15.sp,
                            lineHeight = 15.sp,
                            maxLines = 1
                        )
                    }
                    Spacer(
                        Modifier.height(20.dp)
                    )
                    Text(
                        "${data.location.city}, ${data.location.country}",
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}