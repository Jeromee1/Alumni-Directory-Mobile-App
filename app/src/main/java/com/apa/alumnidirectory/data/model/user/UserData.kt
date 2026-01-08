package com.apa.alumnidirectory.data.model.user

import com.apa.alumnidirectory.data.enums.PreferredContact
import com.apa.alumnidirectory.data.enums.Roles
import com.apa.alumnidirectory.data.enums.Status

data class UserData(
    val uid: String = "",
    val fullName: String = "",
    val fullNameLower: String = "",
    val email: String = "",
    val status: String = Status.PENDING.value,
    val role: String = Roles.ALUMNI.value,
    val graduationYear: String = "",
    val department: String = "",
    val position: String = "",
    val company: String = "",
    val primaryStack: String = "",
    val location: Location = Location(),
    val preferredContact: String = PreferredContact.EMAIL.value,
    val contact: ContactInfo = ContactInfo(),
    val bio: String? = null,
    val photoUrl: Int? = null,
    val rejectionMsg: String? = null,
    val createdAt: Long = 0L,
    val lastUpdated: Long = 0L,
    val approvedAt: Long? = null
) {
    fun toMap(): Map<String, Any> {
        val map = mutableMapOf(
            "uid" to uid,
            "fullName" to fullName,
            "fullNameLower" to fullNameLower,
            "email" to email,
            "status" to status,
            "role" to role,
            "graduationYear" to graduationYear,
            "department" to department,
            "position" to position,
            "company" to company,
            "primaryStack" to primaryStack,
            "location" to location.toMap(),
            "preferredContact" to preferredContact,
            "contact" to contact.toMap(),
            "createdAt" to createdAt,
            "lastUpdated" to lastUpdated
        )
        bio?.let { map["bio"] = it }
        photoUrl?.let { map["photoUrl"] = it }
        approvedAt?.let { map["approvedAt"] = it }
        rejectionMsg?.let { map["rejectionMsg"] = it }

        return map
    }
}
