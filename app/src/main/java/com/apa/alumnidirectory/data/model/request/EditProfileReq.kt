package com.apa.alumnidirectory.data.model.request

import com.apa.alumnidirectory.data.model.user.ContactInfo
import com.apa.alumnidirectory.data.model.user.Location

data class EditProfileReq(
    val department: String,
    val position: String,
    val company: String,
    val primaryStack: String,
    val location: Location,
    val preferredContact: String,
    val contact: ContactInfo,
    val bio: String?,
    val photoUrl: Int,
    val rejectionMsg: String?,
    val lastUpdated: Long
) {
    fun toMap(): Map<String, Any> {
        val map = mutableMapOf(
            "department" to department,
            "position" to position,
            "company" to company,
            "primaryStack" to primaryStack,
            "location" to location.toMap(),
            "preferredContact" to preferredContact,
            "contact" to contact.toMap(),
            "photoUrl" to photoUrl,
            "lastUpdated" to lastUpdated
        )
        bio?.let { map["bio"] = it }
        rejectionMsg?.let { map["rejectionMsg"] = it }

        return map
    }
}