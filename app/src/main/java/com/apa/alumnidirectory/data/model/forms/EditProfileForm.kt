package com.apa.alumnidirectory.data.model.forms

import com.apa.alumnidirectory.data.model.user.ContactInfo
import com.apa.alumnidirectory.data.model.user.Location

data class EditProfileForm(
    val department: String = "",
    val position: String = "",
    val company: String = "",
    val primaryStack: String = "",
    val location: Location = Location(),
    val preferredContact: String = "",
    val contact: ContactInfo = ContactInfo(),
    val bio: String? = null,
    val photoUrl: Int = 0,
    val rejectionMsg: String? = null,
)
