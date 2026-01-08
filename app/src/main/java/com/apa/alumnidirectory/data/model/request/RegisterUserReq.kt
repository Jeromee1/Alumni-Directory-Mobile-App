package com.apa.alumnidirectory.data.model.request

import com.apa.alumnidirectory.data.enums.PreferredContact

data class RegisterUserReq(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val password2: String = "",
    val graduationYear: String = "",
    val department: String = "",
    val position: String = "",
    val company: String = "",
    val techStack: String = "",
    val state: String = "",
    val country: String = "",
    val contactPreference: String = PreferredContact.entries.first().value,
    val createdAt: Long = System.currentTimeMillis(),
    val lastUpdated: Long = System.currentTimeMillis()
)