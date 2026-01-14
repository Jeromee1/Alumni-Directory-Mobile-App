package com.apa.alumnidirectory.data.model.user

data class Location(
    val state: String = "",
    val country: String = ""
) {
    fun toMap() = mapOf(
        "state" to state,
        "country" to country
    )
}
