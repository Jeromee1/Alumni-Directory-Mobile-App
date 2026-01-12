package com.apa.alumnidirectory.ui.screens.profile.edit

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.apa.alumnidirectory.data.enums.PreferredContact
import com.apa.alumnidirectory.data.enums.Status
import com.apa.alumnidirectory.data.model.forms.AdminEditProfileForm
import com.apa.alumnidirectory.data.model.forms.EditProfileForm
import com.apa.alumnidirectory.data.model.ui.Country
import com.apa.alumnidirectory.data.model.ui.FieldData
import com.apa.alumnidirectory.data.model.ui.State
import com.apa.alumnidirectory.data.utils.generateGradYears
import com.apa.alumnidirectory.ui.components.bottomsheet.CustomBottomSheet
import com.apa.alumnidirectory.ui.components.bottomsheet.sheetcontent.ProfileImageSheetContent
import com.apa.alumnidirectory.ui.components.confirmation.CustomDialog
import com.apa.alumnidirectory.ui.components.core.LoadingIcon
import com.apa.alumnidirectory.ui.components.inputs.CustomDropdown
import com.apa.alumnidirectory.ui.components.inputs.CustomTextFieldBox
import com.apa.alumnidirectory.ui.components.pfp.DefaultPfp
import com.apa.alumnidirectory.ui.components.pfp.Pfp1
import com.apa.alumnidirectory.ui.components.pfp.Pfp2
import com.apa.alumnidirectory.ui.theme.Primary
import com.apa.alumnidirectory.ui.theme.Text1
import com.apa.alumnidirectory.ui.uiutils.sortWithOtherLast
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var form by remember { mutableStateOf(EditProfileForm()) }
    var adminForm by remember { mutableStateOf(AdminEditProfileForm()) }
    var isLoading by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }

    val countryNames = viewModel.countries
    val selectedCountry by viewModel.selectedCountry.collectAsStateWithLifecycle()
    val states by viewModel.availableStates.collectAsStateWithLifecycle()
    val selectedState by viewModel.selectedState.collectAsStateWithLifecycle()
    val stacks by viewModel.techStacks.collectAsStateWithLifecycle()
    val departments by viewModel.departments.collectAsStateWithLifecycle()

    val perms = viewModel.permissionCheck()
    val user = viewModel.user.collectAsStateWithLifecycle().value
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        viewModel.finish.collect {
            navController.popBackStack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.toast.collect { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(user) {
        if (user != null) {
            form = form.copy(
                department = user.department,
                position = user.position,
                company = user.company,
                primaryStack = user.primaryStack,
                location = user.location,
                preferredContact = user.preferredContact,
                contact = user.contact,
                bio = user.bio,
                photoUrl = user.photoUrl
            )
            adminForm = adminForm.copy(
                fullName = user.fullName,
                status = user.status,
                graduationYear = user.graduationYear
            )
            isLoading = false
        } else {
            isLoading = true
        }
    }

    if (!isLoading && user != null) {

        EditProfile(
            perms,
            form,
            { form = it },
            adminForm,
            { adminForm = it },
            countryNames,
            selectedCountry,
            viewModel::onCountrySelected,
            states,
            selectedState,
            viewModel::onStateSelected,
            stacks,
            departments,
            { showDialog = true }
        ) { scope.launch { bottomSheetState.show() } }

        CustomBottomSheet(
            bottomSheetState,
            { scope.launch { bottomSheetState.hide() } }
        ) {
            ProfileImageSheetContent(form.photoUrl)
            { form = form.copy(photoUrl = it) }
        }

        if (showDialog) {
            CustomDialog(
                { showDialog = false },
                {
                    //Save and navigate
                    if (perms) viewModel.adminUpdateUser(form, adminForm)
                    else viewModel.updateUser(form)
                    showDialog = false
                },
                "Save changes?",
                "You can update you profile anytime.",
                Icons.Filled.Save
            )
        }
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
    perms: Boolean,
    form: EditProfileForm,
    formOnChange: (EditProfileForm) -> Unit,
    adminForm: AdminEditProfileForm,
    adminFormOnChange: (AdminEditProfileForm) -> Unit,
    countryNames: List<Country>,
    selectedCountry: Country?,
    onSelectedCountry: (Country) -> Unit,
    states: List<State>,
    selectedState: State?,
    onSelectedState: (State) -> Unit,
    stack: List<String>,
    department: List<String>,
    openDialog: () -> Unit,
    openBottomSheet: () -> Unit
) {
    form.apply {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 80.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Section1
                Box {
                    Box(
                        modifier = Modifier
                            .padding(18.dp)
                            .size(120.dp)
                            .aspectRatio(1f)
                            .background(Color.Gray, RoundedCornerShape(12.dp))
                            .border(2.dp, Color.LightGray, RoundedCornerShape(12.dp))
                            .clickable { openBottomSheet() }
                    ) {
                        when (photoUrl) {
                            0 -> DefaultPfp()
                            1 -> Pfp1()
                            2 -> Pfp2()
                        }
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
                        FieldData("Company", company)
                        { formOnChange(copy(company = it)) },
                        FieldData("Position", position)
                        { formOnChange(copy(position = it)) },
                        FieldData(
                            "Department",
                            form.department,
                            list = department.sortWithOtherLast()
                        )
                        { formOnChange(copy(department = it)) },
                        FieldData(
                            "Tech Stack",
                            form.primaryStack,
                            list = stack.sortWithOtherLast()
                        )
                        { formOnChange(copy(primaryStack = it)) },
                    )
                )
                HorizontalDivider(thickness = 1.dp)
                //Section3
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Location",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    CustomDropdown(
                        items = countryNames,
                        selectedItem = selectedCountry?.name ?: "Select a Country",
                        itemLabel = { it.name },
                        onSelectedChange = {
                            copy(location = location.copy(country = it.name))
                            onSelectedCountry(it)
                        }
                    )
                    CustomDropdown(
                        items = states,
                        selectedItem = selectedState?.name ?: "Select a State",
                        itemLabel = { it.name },
                        onSelectedChange = { stateName ->
                            copy(location = location.copy(state = stateName.name))
                            onSelectedState(stateName)
                        }
                    )
                    HorizontalDivider(thickness = 1.dp)
                    //Section4
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Contact Preference",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "Display Email",
                                    color = Text1
                                )
                                Switch(
                                    checked = contact.showEmail,
                                    onCheckedChange = { isChecked ->
                                        formOnChange(
                                            copy(
                                                contact = contact.copy(showEmail = isChecked)
                                            )
                                        )
                                    }
                                )
                            }
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "Display Phone",
                                    color = Text1
                                )
                                Switch(
                                    checked = contact.showPhone,
                                    onCheckedChange = { isChecked ->
                                        formOnChange(
                                            copy(
                                                contact = contact.copy(showPhone = isChecked)
                                            )
                                        )
                                    }
                                )
                            }
                        }
                        CustomDropdown(
                            PreferredContact.entries.map { it.value },
                            preferredContact,
                            itemLabel = { it }
                        )
                        { formOnChange(copy(preferredContact = it)) }
                    }
                    HorizontalDivider(thickness = 1.dp)
                    //Section5
                    CustomTextFieldBox(
                        "Contacts",
                        fields = listOf(
                            FieldData("Phone", contact.phone ?: "")
                            { formOnChange(copy(contact = contact.copy(phone = it))) },
                            FieldData("LinkedIn", contact.linkedIn ?: "")
                            { formOnChange(copy(contact = contact.copy(linkedIn = it))) },
                            FieldData("GitHub", contact.github ?: "")
                            { formOnChange(copy(contact = contact.copy(github = it))) },
                            FieldData("Website", contact.website ?: "")
                            { formOnChange(copy(contact = contact.copy(website = it))) }
                        )
                    )
                    HorizontalDivider(thickness = 1.dp)
                    //Section6
                    CustomTextFieldBox(
                        categoryName = "Additional Information",
                        fields = listOf(
                            FieldData("Bio", bio ?: "")
                            { formOnChange(copy(bio = it)) },
                        )
                    )
                    if (perms) {
                        HorizontalDivider(thickness = 1.dp)
                        //Section6
                        CustomTextFieldBox(
                            categoryName = "Admin Configurations",
                            fields = listOf(
                                FieldData("FullName", adminForm.fullName)
                                { adminFormOnChange(adminForm.copy(fullName = it)) },
                            )
                        )
                        ///Status & Graduation Year dropdowns here
                        CustomDropdown(
                            items = Status.entries,
                            selectedItem = adminForm.status.replaceFirstChar { it.uppercase() },
                            itemLabel = { word -> word.value.replaceFirstChar { it.uppercase() } },
                            onSelectedChange = {
                                adminFormOnChange(adminForm.copy(status = it.value))
                            }
                        )
                        CustomDropdown(
                            items = generateGradYears(),
                            selectedItem = adminForm.graduationYear,
                            itemLabel = { it.toString() },
                            onSelectedChange = {
                                adminFormOnChange(adminForm.copy(graduationYear = it.toString()))
                            }
                        )
                    }
                }
            }
            FloatingActionButton(
                onClick = { openDialog() },
                containerColor = Primary,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp)
                    .size(80.dp)
                    .padding(8.dp),
                shape = RoundedCornerShape(100)
            ) {
                Icon(
                    Icons.Filled.Save, "",
                    modifier = Modifier.size(44.dp)
                )
            }
        }
    }
}