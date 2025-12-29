package com.apa.alumnidirectory.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.apa.alumnidirectory.data.enums.PreferredContact
import com.apa.alumnidirectory.data.model.customtextfield.FieldData
import com.apa.alumnidirectory.data.model.user.ContactInfo
import com.apa.alumnidirectory.data.model.user.Location
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.ui.components.core.HomeUserCard
import com.apa.alumnidirectory.ui.components.inputs.CustomFilterButton
import com.apa.alumnidirectory.ui.components.inputs.CustomTextField
import com.apa.alumnidirectory.ui.theme.Primary
import kotlin.String

@Composable
fun HomeScreen(
    navController: NavController
) {
    var search by remember { mutableStateOf("") }

    val tempUsers = listOf(
        UserData(
            uid = "123",
            fullName = "John Doe",
            fullNameLower = "john doe",
            email = "johndoe@gmail.com",
            status = "approved",
            role = "admin",
            graduationYear = "2025",
            department = "software stuff",
            position = "doggy",
            company = "SafeTruck",
            primaryStack = "thaddeous",
            location = Location(
                "Kuala Lumpur", "Malaysia"
            ),
            preferredContact = PreferredContact.EMAIL.value,
            contact = ContactInfo(),
            bio = null,
            photoUrl= null,
        ),
        UserData(
            uid = "123",
            fullName = "Jane Doe",
            fullNameLower = "jane doe",
            email = "janedoe@gmail.com",
            status = "approved",
            role = "user",
            graduationYear = "2025",
            department = "software stuff",
            position = "CEO",
            company = "Intel",
            primaryStack = "Fullstack",
            location = Location(
                "Kuala Lumpur", "Malaysia"
            ),
            preferredContact = PreferredContact.EMAIL.value,
            contact = ContactInfo(),
            bio = null,
            photoUrl= null,
        )
    )

    Home(tempUsers, search) { search = it }
}

@Composable
fun Home(
    users: List<UserData>,
    search: String,
    onSearchChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.11f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(0.6f)
                ) {
                    CustomTextField(
                        FieldData(
                            "Search",
                            search,
                            onSearchChange
                        )
                    )
                }

                CustomFilterButton {

                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(users) {
                    HomeUserCard(it) {  }
                }
            }
        }
        FloatingActionButton(
            onClick = {  },
            containerColor = Primary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(80.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(100)
        ) {
            Icon(
                Icons.Outlined.Person, "",
                modifier = Modifier.size(44.dp)
            )
        }
    }
}