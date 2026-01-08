package com.apa.alumnidirectory.ui.screens.admin.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.apa.alumnidirectory.data.model.ui.FieldData
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.ui.components.core.AdminManageUserCard
import com.apa.alumnidirectory.ui.components.inputs.CustomFilterButton
import com.apa.alumnidirectory.ui.components.inputs.CustomTextField
import com.apa.alumnidirectory.ui.theme.SecondaryG

@Composable
fun AdminManageScreen(
    navController: NavController
) {
    var search by remember { mutableStateOf("") }

    val tempUsers = listOf(
        UserData(
            uid = "123",
            fullName = "John Doe",
            graduationYear = "2025"
        ),

    )

    AdminManage(tempUsers, search) { search = it }
}

@Composable
fun AdminManage(
    users: List<UserData>,
    search: String,
    onSearchChange: (String) -> Unit
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
                "Manage Users: ${users.size}",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier.weight(0.7f)
            ) {
                CustomTextField(
                    FieldData("Search", search, onSearchChange)
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight()
            ) {
                CustomFilterButton { }
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(users) { user ->
                AdminManageUserCard(user) { /* Navigate to profile page */ }
            }
        }
    }
}