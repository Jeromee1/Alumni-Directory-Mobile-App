package com.apa.alumnidirectory.data.model.request

import com.apa.alumnidirectory.data.enums.PreferredContact
import com.apa.alumnidirectory.data.enums.Roles
import com.apa.alumnidirectory.data.enums.Status
import com.apa.alumnidirectory.data.model.user.ContactInfo
import com.apa.alumnidirectory.data.model.user.Location

data class RegisterUserReq(
    val uid: String,
    val fullName: String,
    val email: String,
    val password: String,
    val password2: String,
    val graduationYear: String,
    val department: String,
    val position: String,
    val company: String,
    val techStack: String,
    val location: Location,
    val contactInfo: ContactInfo,
    val photoUrl: Int,
    val preferredContact: String = PreferredContact.entries.first().value,
    val createdAt: Long,
    val lastUpdated: Long,
    val status: String = Status.PENDING.value,
    val role: String = Roles.ALUMNI.value,

    ) {
    fun toMap(): Map<String, Any> = mutableMapOf(
        "uid" to uid,
        "fullName" to fullName,
        "email" to email,
        "status" to status,
        "role" to role,
        "graduationYear" to graduationYear,
        "department" to department,
        "position" to position,
        "company" to company,
        "primaryStack" to techStack,
        "location" to location.toMap(),
        "preferredContact" to preferredContact,
        "contact" to contactInfo.toMap(),
        "photoUrl" to photoUrl,
        "createdAt" to createdAt,
        "lastUpdated" to lastUpdated
    )
}