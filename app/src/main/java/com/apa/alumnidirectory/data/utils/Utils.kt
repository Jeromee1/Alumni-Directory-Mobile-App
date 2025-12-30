package com.apa.alumnidirectory.data.utils

import com.apa.alumnidirectory.data.enums.Roles
import com.apa.alumnidirectory.data.enums.Status
import com.apa.alumnidirectory.data.model.auth.RegisterUserReq
import com.apa.alumnidirectory.data.model.user.ContactInfo
import com.apa.alumnidirectory.data.model.user.Location
import com.apa.alumnidirectory.data.model.user.UserData

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
        Location(req.city, req.country),
        req.contactPreference,
        ContactInfo(),
        null,
        null,
        null,
        req.createdAt,
        null
    )
}