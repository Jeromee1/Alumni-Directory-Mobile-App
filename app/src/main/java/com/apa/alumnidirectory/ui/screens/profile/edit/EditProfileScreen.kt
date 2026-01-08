package com.apa.alumnidirectory.ui.screens.profile.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.apa.alumnidirectory.data.model.request.EditProfileReq
import com.apa.alumnidirectory.data.model.ui.FieldData
import com.apa.alumnidirectory.data.model.user.UserData
import com.apa.alumnidirectory.ui.components.core.LoadingIcon
import com.apa.alumnidirectory.ui.components.inputs.CustomTextFieldBox

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    var form by remember { mutableStateOf(EditProfileReq()) }

    val user = viewModel.user.collectAsStateWithLifecycle().value

    if(user != null) {
        EditProfile(user, form) { form = it }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingIcon()
        }
    }
}

@Composable
fun EditProfile(
    user: UserData,
    form: EditProfileReq,
    formOnChange: (EditProfileReq) -> Unit
) {
    form.apply {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Section1
            Box{
                Box(
                    modifier = Modifier
                        .padding(18.dp)
                        .size(120.dp)
                        .aspectRatio(1f)
                        .background(Color.Gray, RoundedCornerShape(12.dp))
                        .border(2.dp, Color.LightGray, RoundedCornerShape(12.dp))
                ) {
                    //Temp Pfp Image
                    Icon(
                        Icons.Filled.Person, "",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Icon(
                    Icons.Filled.Edit,
                    "",
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.BottomEnd)
                )
            }
            HorizontalDivider(thickness = 1.dp)
            //Section2
            CustomTextFieldBox(
                categoryName = "Job Information",
                fields = listOf(
                    FieldData("Depart", department)
                    { formOnChange(copy(department = it)) },
                    FieldData("Position", position)
                    { formOnChange(copy(position = it)) },
                    FieldData("Company", company)
                    { formOnChange(copy(company = it)) },
                    FieldData("Stack", primaryStack)
                    { formOnChange(copy(primaryStack = it)) },
                )
            )
            HorizontalDivider(thickness = 1.dp)
            //Section3
//            CustomTextFieldBox(
//                categoryName = "Location Information",
//                fields = listOf(
//                    FieldData("Location", location)
//                    { formOnChange(copy(location = it)) }
//                )
//            )
            HorizontalDivider(thickness = 1.dp)
            //Section4
            CustomTextFieldBox(
                categoryName = "Contact Information",
                fields = listOf(
//                    FieldData("Contact", contact)
//                    { formOnChange(copy(contact = it)) },
                )
            )
            HorizontalDivider(thickness = 1.dp)
            //Section5
            CustomTextFieldBox(
                categoryName = "Additional Information",
                fields = listOf(
                    FieldData("Bio", bio ?: "")
                    { formOnChange(copy(bio = it)) },
                )
            )
        }
    }
}

/*
Column(
     modifier = Modifier
         .fillMaxWidth()
         .padding(20.dp, 0.dp),
     verticalArrangement = Arrangement.spacedBy(8.dp),
     horizontalAlignment = Alignment.CenterHorizontally
) {

}
*/