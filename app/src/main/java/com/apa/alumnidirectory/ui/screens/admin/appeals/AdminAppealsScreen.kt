package com.apa.alumnidirectory.ui.screens.admin.appeals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.apa.alumnidirectory.data.model.request.AppealReq
import com.apa.alumnidirectory.ui.components.core.AdminAppealsUserCard
import com.apa.alumnidirectory.ui.theme.SecondaryG

@Composable
fun AdminAppealsScreen(
    navController: NavController
) {
    val tempAppealReq = listOf(
        AppealReq(
            name = "John Doe",
            email = "johndoe@gmail.com",
            msg = "Pls PLS PLS LET ME INNNNN. I PWOMISE I WON'T DO IT AGAIN!!1!"
        )
    )

    AdminAppeals(tempAppealReq)
}

@Composable
fun AdminAppeals(
    users: List<AppealReq>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(SecondaryG, RoundedCornerShape(12.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Appeals: {Amount here}",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(users) { user ->
                AdminAppealsUserCard(user) { /* navigate to AdminAppealsDetails and pass the string */ }
            }
        }
    }
}