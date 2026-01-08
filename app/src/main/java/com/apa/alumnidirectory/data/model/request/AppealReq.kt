package com.apa.alumnidirectory.data.model.request

data class AppealReq(
    val uid: String = "",
    val userUid: String = "",
    val name: String = "",
    val email: String = "",
    val msg: String = "",
    var resolved: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun toMap() = mutableMapOf<String, Any>(
        "uid" to uid,
        "userUid" to userUid,
        "name" to name,
        "email" to email,
        "msg" to msg,
        "resolved" to resolved,
        "createdAt" to createdAt
    )
}
