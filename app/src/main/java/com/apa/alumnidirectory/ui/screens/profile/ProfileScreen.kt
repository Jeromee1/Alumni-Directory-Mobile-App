package com.apa.alumnidirectory.ui.screens.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Gite
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.ui.components.confirmation.CustomDialog
import com.apa.alumnidirectory.ui.components.core.LoadingIcon
import com.apa.alumnidirectory.ui.components.pfp.DefaultPfp
import com.apa.alumnidirectory.ui.components.pfp.Pfp1
import com.apa.alumnidirectory.ui.components.pfp.Pfp2
import com.apa.alumnidirectory.ui.components.pfp.Pfp3
import com.apa.alumnidirectory.ui.components.pfp.Pfp4
import com.apa.alumnidirectory.ui.components.pfp.Pfp5
import com.apa.alumnidirectory.ui.nav.Screen
import com.apa.alumnidirectory.ui.theme.Danger
import com.apa.alumnidirectory.ui.theme.Email
import com.apa.alumnidirectory.ui.theme.Github
import com.apa.alumnidirectory.ui.theme.LinkedIn
import com.apa.alumnidirectory.ui.theme.Phone
import com.apa.alumnidirectory.ui.theme.Primary
import com.apa.alumnidirectory.ui.theme.Website
import kotlinx.coroutines.flow.filterNotNull

@Composable
fun ProfileScreen(
    isAdmin: Boolean,
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchUser()
    }
    val context = LocalContext.current

    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    var showDialog by remember { mutableStateOf(false) }
    var dialogText by remember { mutableStateOf("") }
    var dialogIcon by remember { mutableStateOf(Icons.Filled.Phone) }
    var dialogTitle by remember { mutableStateOf("") }

    val perms = viewModel.permissionCheck()

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.firebaseUser.filterNotNull().collect {
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        viewModel.firebaseUser.collect {
            if (it == null && !isLoading) {
                navController.navigate(Screen.Login) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }

    val _user by viewModel.user.collectAsStateWithLifecycle()
    val user = _user
    if (user != null) {
        Profile(
            isAdmin,
            user,
            perms,
            { dialogTitle = it },
            { dialogText = it },
            { dialogIcon = it },
            { label, text ->
                clipboard.setPrimaryClip(ClipData.newPlainText(label, text))
                Toast.makeText(context, "Copied to Clipboard", Toast.LENGTH_SHORT)
                    .show()
            },
            viewModel::signOut,
            { showDialog = true }) {
            navController.navigate(Screen.EditProfile(user.uid))
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingIcon()
        }
    }
    if (showDialog) {
        CustomDialog(
            { showDialog = false },
            { showDialog = false },
            dialogTitle,
            dialogText,
            dialogIcon,
            false
        )
    }
}


@Composable
fun Profile(
    isAdmin: Boolean,
    user: UserData,
    perms: Boolean,
    dialogTitleChange: (String) -> Unit,
    dialogTextChange: (String) -> Unit,
    dialogIconChange: (ImageVector) -> Unit,
    copyToClipboard: (String, String) -> Unit,
    logout: () -> Unit,
    showDialog: () -> Unit,
    navToEdit: () -> Unit
) {
    fun dialogTrigger(title: String, data: String, vector: ImageVector) {
        dialogTitleChange(title)
        dialogTextChange(data)
        dialogIconChange(vector)
        copyToClipboard(title, data)
        showDialog()
    }
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
                    when (user.photoUrl) {
                        1 -> Pfp1()
                        2 -> Pfp2()
                        3 -> Pfp3()
                        4 -> Pfp4()
                        5 -> Pfp5()
                        else -> DefaultPfp()
                    }
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
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (user.contact.showPhone && user.contact.phone != null) {
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
                                .clickable {
                                    dialogTrigger(
                                        "Phone",
                                        user.contact.phone,
                                        Icons.Filled.Phone
                                    )
                                }
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
                    if (user.contact.showEmail) {
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
                                .clickable {
                                    dialogTrigger(
                                        "Email",
                                        user.email,
                                        Icons.Filled.Email
                                    )
                                }
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
                    if (!user.contact.github.isNullOrBlank()) {
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
                                .clickable {
                                    dialogTrigger(
                                        "Phone",
                                        user.contact.github,
                                        Icons.Filled.Gite
                                    )
                                }
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
                    if (!user.contact.linkedIn.isNullOrBlank()) {
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
                                .clickable {
                                    dialogTrigger(
                                        "Phone",
                                        user.contact.linkedIn,
                                        Icons.Filled.Link
                                    )
                                }
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
                    if (!user.contact.website.isNullOrBlank()) {
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
                                .clickable {
                                    dialogTrigger(
                                        "Phone",
                                        user.contact.website,
                                        Icons.Default.Language
                                    )
                                }
                        ) {
                            Icon(
                                Icons.Default.Language,
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
                    .padding(start = 20.dp, end = 20.dp, bottom = 88.dp),
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
        if (perms) {
            FloatingActionButton(
                onClick = { logout() },
                containerColor = Danger,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
                    .size(80.dp)
                    .padding(8.dp),
                shape = RoundedCornerShape(100)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Logout,
                    "",
                    modifier = Modifier.size(44.dp)
                )
            }
        }
        if (perms || isAdmin)
            FloatingActionButton(
                onClick = { navToEdit() },
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