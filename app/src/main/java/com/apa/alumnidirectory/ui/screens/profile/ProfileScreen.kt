package com.apa.alumnidirectory.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Gite
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.apa.alumnidirectory.data.enums.PreferredContact
import com.apa.alumnidirectory.data.model.user.ContactInfo
import com.apa.alumnidirectory.data.model.user.Location
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.ui.theme.Email
import com.apa.alumnidirectory.ui.theme.Github
import com.apa.alumnidirectory.ui.theme.LinkedIn
import com.apa.alumnidirectory.ui.theme.Phone
import com.apa.alumnidirectory.ui.theme.Primary
import com.apa.alumnidirectory.ui.theme.Website

@Composable
fun ProfileScreen(
//    uid: String,
    navController: NavController
) {
    val tempUser = UserData(
        uid = "123",
        fullName = "John Doe",
        fullNameLower = "john doe",
        email = "johndoe@gmail.com",
        status = "Approved",
        role = "CEO",
        graduationYear = "2025",
        department = "Store",
        position = "Doggy",
        company = "Alone",
        primaryStack = "Overflow",
        location = Location(
            "Kuala Lumpur",
            "Yemen"
        ),
        preferredContact = PreferredContact.EMAIL.value,
        contact = ContactInfo(
            true,
            "012 3456 7890",
            true,
            "linkedin.com",
            "github.com",
            "mywebsite.com.my.cat.to.vet.now"
        ),
        bio = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        photoUrl = 2,
        createdAt = 12528582385L,
        approvedAt = 1286883853L
    )

    Profile(tempUser)
}

@Composable
fun Profile(
    user: UserData
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            //Section 1
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(20.dp, 0.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(105.dp)
                        .aspectRatio(1f)
                        .background(Color.Gray, RoundedCornerShape(12.dp))
                        .border(2.dp, Color.LightGray, RoundedCornerShape(12.dp))
                ) {
                    Icon(
                        Icons.Filled.Person, "",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        user.fullName,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        user.graduationYear,
                        fontSize = 22.sp
                    )
                }
            }
            HorizontalDivider(thickness = 1.dp)
            //Section2
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    "Job Information",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Company: ${user.company}",
                    fontSize = 18.sp
                )
                Text(
                    "Position: ${user.position}",
                    fontSize = 18.sp
                )
                Text(
                    "Stack: ${user.primaryStack}",
                    fontSize = 18.sp
                )
            }
            HorizontalDivider(thickness = 1.dp)
            //Section3
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp)
            ) {
                Text(
                    "Location Information",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "${user.location.state}, ${user.location.country}",
                    fontSize = 18.sp
                )
            }
            HorizontalDivider(thickness = 1.dp)
            //Section4
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Contact Information",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if(user.contact.showPhone) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Phone,
                                    RoundedCornerShape(12.dp)
                                )
                                .border(
                                    2.dp,
                                    Color.LightGray,
                                    RoundedCornerShape(12.dp)
                                )
                        ) {
                            Icon(
                                Icons.Filled.Phone,
                                "",
                                modifier = Modifier
                                    .size(50.dp)
                                    .padding(6.dp)
                            )
                        }
                    }
                    if(user.contact.showEmail) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Email,
                                    RoundedCornerShape(12.dp)
                                )
                                .border(
                                    2.dp,
                                    Color.LightGray,
                                    RoundedCornerShape(12.dp)
                                )
                        ) {
                            Icon(
                                Icons.Filled.Email,
                                "",
                                modifier = Modifier
                                    .size(50.dp)
                                    .padding(6.dp)
                            )
                        }
                    }
                    if(!user.contact.github.isNullOrBlank()) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Github,
                                    RoundedCornerShape(12.dp)
                                )
                                .border(
                                    2.dp,
                                    Color.LightGray,
                                    RoundedCornerShape(12.dp)
                                )
                        ) {
                            Icon(
                                Icons.Filled.Gite,
                                "",
                                modifier = Modifier
                                    .size(50.dp)
                                    .padding(6.dp)
                            )
                        }
                    }
                    if(!user.contact.linkedIn.isNullOrBlank()) {
                        Box(
                            modifier = Modifier
                                .background(
                                    LinkedIn,
                                    RoundedCornerShape(12.dp)
                                )
                                .border(
                                    2.dp,
                                    Color.LightGray,
                                    RoundedCornerShape(12.dp)
                                )
                        ) {
                            Icon(
                                Icons.Filled.Link,
                                "",
                                modifier = Modifier
                                    .size(50.dp)
                                    .padding(6.dp)
                            )
                        }
                    }
                    if(!user.contact.website.isNullOrBlank()) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Website,
                                    RoundedCornerShape(12.dp)
                                )
                                .border(
                                    2.dp,
                                    Color.LightGray,
                                    RoundedCornerShape(12.dp)
                                )
                        ) {
                            Icon(
                                Icons.Filled.Web,
                                "",
                                modifier = Modifier
                                    .size(50.dp)
                                    .padding(6.dp)
                            )
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    "Preferred: ${user.preferredContact}",
                    fontSize = 18.sp
                )
            }
            HorizontalDivider(thickness = 1.dp)
            //Section5
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "About Me",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    user.bio ?: "-",
                    fontSize = 18.sp
                )
            }
            Spacer(Modifier.height(40.dp))
        }
        FloatingActionButton(
            onClick = { /* Nav to edit profile */ },
            containerColor = Primary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .size(80.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(100)
        ) {
            Icon(
                Icons.Filled.Edit, "",
                modifier = Modifier.size(44.dp)
            )
        }
    }
}