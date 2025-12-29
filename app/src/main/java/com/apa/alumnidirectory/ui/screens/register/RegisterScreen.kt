package com.apa.alumnidirectory.ui.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.apa.alumnidirectory.R
import com.apa.alumnidirectory.data.enums.PreferredContact
import com.apa.alumnidirectory.data.model.auth.RegisterUserReq
import com.apa.alumnidirectory.data.model.customtextfield.FieldData
import com.apa.alumnidirectory.ui.components.inputs.CustomDropdown
import com.apa.alumnidirectory.ui.components.inputs.CustomTextFieldBox
import com.apa.alumnidirectory.ui.theme.SecondaryG

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    var form by remember { mutableStateOf(RegisterUserReq()) }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.finish.collect {
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.25f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.alumni_directory_logo),
                contentDescription = "",
                modifier = Modifier.size(140.dp)
            )
        }
        Spacer(Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "Already have an account?",
                fontSize = 16.sp
            )
            TextButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text(
                    "Login",
                    color = Color.Cyan,
                    fontSize = 16.sp
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            form.apply {
                CustomTextFieldBox(
                    categoryName = "Basic Info",
                    fields = listOf(
                        FieldData("Full Name", fullName)
                        { form = copy(fullName = it) },
                        FieldData("Email", email)
                        { form = copy(email = it) },
                        FieldData("Password", password)
                        { form = copy(password = it) },
                        FieldData("Confirm Password", password2)
                        { form = copy(password2 = it)}
                    )
                )

                CustomTextFieldBox(
                    categoryName = "Academic Info",
                    fields = listOf(
                        FieldData("Graduation Year", graduationYear)
                        { form = copy(graduationYear = it) },
                        FieldData("Department", department)
                        { form = copy(department = it) }
                    )
                )

                CustomTextFieldBox(
                    categoryName = "Professional Info",
                    fields = listOf(
                        FieldData("Position", position)
                        { form = copy(position = it) },
                        FieldData("Company", company)
                        { form = copy(company = it) },
                        FieldData("Tech Stack", techStack)
                        { form = copy(techStack = it) }
                    )
                )

                CustomTextFieldBox(
                    categoryName = "Location",
                    fields = listOf(
                        FieldData("City", city)
                        { form = copy(city = it) },
                        FieldData("Country", country)
                        { form = copy(country = it) },
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp, 16.dp)
                        .shadow(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SecondaryG, RoundedCornerShape(12.dp))
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Contact Preference",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        CustomDropdown(
                            PreferredContact.entries.map { it.value },
                            contactPreference,
                        )
                        { form = copy(contactPreference = it) }
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        modifier = Modifier
                            .width(300.dp)
                            .padding(60.dp),
                        shape = RoundedCornerShape(12.dp),
                        onClick = {
                            viewModel.register(form)
                        }
                    ) {
                        Text(
                            "Register",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}
