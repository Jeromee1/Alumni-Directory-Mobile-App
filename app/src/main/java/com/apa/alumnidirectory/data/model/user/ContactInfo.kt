package com.apa.alumnidirectory.data.model.user

data class ContactInfo(
    val showEmail: Boolean = true,
    val phone: String? = null,
    val showPhone: Boolean = false,
    val linkedIn: String? = null,
    val github: String? = null,
    val website: String? = null
) {
    fun toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>(
            "showEmail" to showEmail,
            "showPhone" to showPhone,
        )
        phone?.let { map["phone"] = it }
        linkedIn?.let { map["linkedIn"] = it }
        github?.let { map["github"] = it }
        website?.let { map["website"] = it }
        return map
    }
}
