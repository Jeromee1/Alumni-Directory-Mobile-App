package com.apa.alumnidirectory.data.model.request

data class AppealReq(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val msg: String = "",
    val isResolved: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun toMap() = mutableMapOf<String, Any>(
        "uid" to uid,
        "name" to name,
        "email" to email,
        "msg" to msg,
        "isResolved" to isResolved,
        "createdAt" to createdAt
    )
}
