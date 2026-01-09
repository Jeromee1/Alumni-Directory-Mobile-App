package com.apa.alumnidirectory.data.enums

enum class SortAdmin(val value: String) {
    DEFAULT("Default"),
    NAME_ASC("Name A-Z"),
    NAME_DESC("Name Z-A"),
    GRADUATION_YEAR_ASC("Graduation Year Ascending"),
    GRADUATION_YEAR_DESC("Graduation Year Descending"),
    RECENTLY_UPDATED_DESC("Recently Updated Descending"),
    STATUS_ASC("User Status Ascending"),
    STATUS_DESC("User Status Descending"),
}