package com.apa.alumnidirectory.data.model.user

import com.apa.alumnidirectory.data.enums.PreferredContact

data class UserData(
    val uid: String = "",
    val fullName: String = "",
    val fullNameLower: String = "",
    val email: String = "",
    val status: String = "",
    val role: String = "",
    val graduationYear: String = "",
    val department: String = "",
    val position: String = "",
    val company: String = "",
    val primaryStack: String = "",
    val location: Location = Location(),
    val preferredContact: String = PreferredContact.EMAIL.value,
    val contact: ContactInfo = ContactInfo(),
    val bio: String? = null,
    val photoUrl: String? = null,
    val createdAt: Long = 0L,
    val approvedAt: Long? = null
) {
    fun toMap(): Map<String, Any> {
        val map = mutableMapOf(
            "uid" to uid,
            "fullName" to fullName,
            "fullNameLower" to fullNameLower,
            "email" to email,
            "status" to "pending",
            "role" to role,
            "graduationYear" to graduationYear,
            "department" to department,
            "position" to position,
            "company" to company,
            "primaryStack" to primaryStack,
            "location" to location.toMap(),
            "preferredContact" to preferredContact,
            "contact" to contact.toMap(),
            "createdAt" to createdAt
        )
        bio?.let { map["bio"] = it }
        photoUrl?.let { map["photoUrl"] = it }
        approvedAt?.let { map["approvedAt"] = it }

        return map
    }
}
