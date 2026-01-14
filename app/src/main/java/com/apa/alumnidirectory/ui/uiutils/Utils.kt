package com.apa.alumnidirectory.ui.uiutils

import com.apa.alumnidirectory.data.enums.Sort
import com.apa.alumnidirectory.data.model.ui.FilterState
import com.apa.alumnidirectory.data.model.user.UserData

object UserFilter {
    fun filter(users: List<UserData>, filter: FilterState): List<UserData> {
        return users
            .filter {
                filter.query.isBlank() || it.fullName.contains(
                    filter.query,
                    ignoreCase = true
                )
            }
            .filter { filter.country == null || it.location.country == filter.country }
            .filter { filter.state == null || it.location.state == filter.state }
            .filter { filter.year == null || it.graduationYear == filter.year }
            .filter { filter.techStack == null || it.primaryStack == filter.techStack }
            .filter { filter.status == null || it.status == filter.status }
            .let { list ->
                when (filter.sort) {
                    Sort.NAME_ASC.value -> list.sortedBy { it.fullName }
                    Sort.NAME_DESC.value -> list.sortedByDescending { it.fullName }
                    Sort.GRADUATION_YEAR_ASC.value -> list.sortedBy { it.graduationYear }
                    Sort.GRADUATION_YEAR_DESC.value -> list.sortedByDescending { it.graduationYear }
                    Sort.RECENTLY_UPDATED_DESC.value -> list.sortedByDescending { it.lastUpdated }
                    else -> list
                }
            }
    }
}

object FilterPlaceholders {
    const val COUNTRY = "Country"
    const val STATE = "State"
    const val YEAR = "Graduation Year"
    const val STACK = "Tech Stack"
    const val STATUS = "Status"
}

fun timeCheckForAppeal(time: Long): Boolean {
    val hours24 = 24 * 60 * 60 * 1000L
    val now = System.currentTimeMillis()
    return now - time >= hours24
}

fun List<String>.sortWithOtherLast(): List<String> {
    return this.sortedWith(
        compareBy<String> { it.contains("Other", ignoreCase = true) }
            .thenBy { it.lowercase() }
    )
}

