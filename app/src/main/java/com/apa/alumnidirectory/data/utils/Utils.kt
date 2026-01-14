package com.apa.alumnidirectory.data.utils

import android.content.Context
import com.apa.alumnidirectory.data.enums.Roles
import com.apa.alumnidirectory.data.enums.Status
import com.apa.alumnidirectory.data.model.forms.AdminEditProfileForm
import com.apa.alumnidirectory.data.model.forms.EditProfileForm
import com.apa.alumnidirectory.data.model.forms.RegisterForm
import com.apa.alumnidirectory.data.model.request.AdminEditProfileReq
import com.apa.alumnidirectory.data.model.request.EditProfileReq
import com.apa.alumnidirectory.data.model.request.RegisterUserReq
import com.apa.alumnidirectory.data.model.ui.Country
import com.apa.alumnidirectory.data.model.ui.CountryRaw
import com.apa.alumnidirectory.data.model.ui.State
import com.apa.alumnidirectory.data.model.user.ContactInfo
import com.apa.alumnidirectory.data.model.user.Location
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar
import kotlin.String

fun buildUserData(req: RegisterForm, uid: String): RegisterUserReq {
    return RegisterUserReq(
        uid,
        req.fullName,
        req.email,
        Status.PENDING.value,
        Roles.ALUMNI.value,
        req.graduationYear,
        req.department,
        req.position,
        req.company,
        req.techStack,
        Location(req.state, req.country),
        ContactInfo(),
        0,
        req.contactPreference,
        System.currentTimeMillis(),
        System.currentTimeMillis(),
    )
}

fun buildEditReq(data: EditProfileForm): EditProfileReq {
    return EditProfileReq(
        department = data.department,
        position = data.position,
        company = data.company,
        primaryStack = data.primaryStack,
        location = Location(data.location.state, data.location.country),
        preferredContact = data.preferredContact,
        contact = ContactInfo(
            data.contact.showEmail,
            data.contact.phone,
            data.contact.showPhone,
            data.contact.linkedIn,
            data.contact.github,
            data.contact.website
        ),
        bio = data.bio,
        photoUrl = data.photoUrl,
        rejectionMsg = data.rejectionMsg,
        lastUpdated = System.currentTimeMillis()
    )
}

fun buildAdminEditReq(data: AdminEditProfileForm): AdminEditProfileReq {
    return AdminEditProfileReq(
        fullName = data.fullName,
        status = data.status,
        graduationYear = data.graduationYear,
        lastUpdated = System.currentTimeMillis()
    )
}

fun loadCountries(context: Context): List<Country> {
    val json = context.assets
        .open("countries_states.json")
        .bufferedReader()
        .use { it.readText() }

    val countryRaws: List<CountryRaw> = Gson().fromJson(
        json,
        object : TypeToken<List<CountryRaw>>() {}.type
    )

    return countryRaws.map { data ->
        Country(
            name = data.name,
            states = data.states.map { State(it) }
        )
    }
}

fun generateGradYears(
    firstYear: Int = 2021,
    gradMonth: Int = 11
): List<Int> {
    val now = Calendar.getInstance()
    val currentYear = now.get(Calendar.YEAR)
    val currentMonth = now.get(Calendar.MONTH)

    val latestYear = if (currentMonth >= gradMonth) currentYear else currentYear - 1

    return (firstYear..latestYear).toList().reversed()
}
