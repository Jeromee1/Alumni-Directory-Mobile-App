package com.apa.alumnidirectory.data.enums

enum class Sort(val value: String) {
    NAME_ASC("Name A-Z"),
    NAME_DESC("Name Z-A"),
    GRADUATION_YEAR_ASC("Graduation Year Ascending"),
    GRADUATION_YEAR_DESC("Graduation Year Descending"),
    RECENTLY_UPDATED_DESC("Recently Updated Descending"),
}