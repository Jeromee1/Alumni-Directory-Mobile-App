package com.apa.alumnidirectory.data.model.user

data class Location(
    val city: String = "",
    val country: String = ""
) {
    fun toMap() = mapOf(
        "city" to city,
        "country" to country
    )
}
