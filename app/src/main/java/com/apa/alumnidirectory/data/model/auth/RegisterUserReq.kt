package com.apa.alumnidirectory.data.model.auth

data class RegisterUserReq(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val graduationYear: Int = 0,
    val department: String = "",
    val position: String = "",
    val company: String = "",
    val techStack: String = "",
    val city: String = "",
    val country: String = "",
    val contactPreference: String = "",
    val bio: String? = null,
    val photoUrl: String? = null,
)