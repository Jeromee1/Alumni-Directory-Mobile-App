package com.apa.alumnidirectory.data.model.request


data class AdminEditProfileReq(
    val fullName: String,
    val status: String,
    val graduationYear: String,
    val lastUpdated: Long
) {
    fun toMap(): Map<String, Any> = mutableMapOf(
        "fullName" to fullName,
        "status" to status,
        "graduationYear" to graduationYear,
        "lastUpdated" to lastUpdated
    )
}