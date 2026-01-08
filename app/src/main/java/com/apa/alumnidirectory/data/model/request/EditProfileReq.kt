package com.apa.alumnidirectory.data.model.request

import com.apa.alumnidirectory.data.enums.PreferredContact
import com.apa.alumnidirectory.data.enums.Roles
import com.apa.alumnidirectory.data.enums.Status
import com.apa.alumnidirectory.data.model.user.ContactInfo
import com.apa.alumnidirectory.data.model.user.Location

data class EditProfileReq (
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
    val photoUrl: Int = 0,
    val rejectionMsg: String? = null,
    val createdAt: Long = 0L,
    val approvedAt: Long? = null
)