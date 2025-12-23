package com.apa.alumnidirectory.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.apa.alumnidirectory.R
import com.apa.alumnidirectory.ui.theme.Secondary

@Composable
fun LoginScreen(
    navController: NavController
) {
    var _email by remember { mutableStateOf("") }
    var _password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight(0.25f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.alumni_directory_logo),
                    contentDescription = ""
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Don't have an account?",
                    fontSize = 16.sp
                )
                TextButton(
                    onClick = {
                        //Navigate to register
                    }
                ) {
                    Text(
                        "Register",
                        color = Color.Cyan,
                        fontSize = 16.sp
                    )
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(28.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(Secondary, RoundedCornerShape(16.dp))
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        "Login",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    TextField(
                        value = _email,
                        onValueChange = { _email = it },
                        shape = RoundedCornerShape(12.dp),
                        placeholder = { Text(
                            "Email"
                        ) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = _password,
                        onValueChange = { _password = it },
                        shape = RoundedCornerShape(12.dp),
                        placeholder = { Text(
                            "Password"
                        ) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        Button(
            modifier = Modifier.align(Alignment.BottomCenter)
                .width(300.dp)
                .padding(60.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = {
                //Login function stuff
            }
        ) {
            Text(
                "Login",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}