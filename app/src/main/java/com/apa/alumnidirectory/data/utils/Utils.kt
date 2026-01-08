package com.apa.alumnidirectory.data.utils

import android.content.Context
import com.apa.alumnidirectory.data.enums.Roles
import com.apa.alumnidirectory.data.enums.Status
import com.apa.alumnidirectory.data.model.request.RegisterUserReq
import com.apa.alumnidirectory.data.model.ui.Country
import com.apa.alumnidirectory.data.model.ui.CountryRaw
import com.apa.alumnidirectory.data.model.ui.State
import com.apa.alumnidirectory.data.model.user.ContactInfo
import com.apa.alumnidirectory.data.model.user.Location
import com.apa.alumnidirectory.data.model.user.UserData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar

fun buildUserData(req: RegisterUserReq, uid: String): UserData {
    return UserData(
        uid,
        req.fullName,
        req.fullName.lowercase(),
        req.email,
        Status.PENDING.value,
        Roles.ALUMNI.value,
        req.graduationYear,
        req.department,
        req.position,
        req.company,
        req.techStack,
        Location(req.state, req.country),
        req.contactPreference,
        ContactInfo(),
        null,
        0,
        null,
        req.createdAt,
        req.lastUpdated,
        null
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
