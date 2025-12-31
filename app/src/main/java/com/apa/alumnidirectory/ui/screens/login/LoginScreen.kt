package com.apa.alumnidirectory.ui.screens.login

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.apa.alumnidirectory.R
import com.apa.alumnidirectory.data.model.request.LoginReq
import com.apa.alumnidirectory.data.model.customtextfield.FieldData
import com.apa.alumnidirectory.ui.components.inputs.CustomTextFieldBox
import com.apa.alumnidirectory.ui.nav.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.finish.collect {
            navController.navigate(Screen.Pending)
        }
    }

    var form by remember { mutableStateOf(LoginReq()) }

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
                        navController.navigate(Screen.Register)
                    }
                ) {
                    Text(
                        "Register",
                        color = Color.Cyan,
                        fontSize = 16.sp
                    )
                }
            }
            CustomTextFieldBox(
                "Login",
                listOf(
                    FieldData("Email", form.email) { form = form.copy(email = it)},
                    FieldData("Password", form.password) { form = form.copy(password = it) }
                )
            )
        }
        Button(
            modifier = Modifier.align(Alignment.BottomCenter)
                .width(300.dp)
                .padding(60.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = {
                //Login function stuff
                viewModel.login(form)
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