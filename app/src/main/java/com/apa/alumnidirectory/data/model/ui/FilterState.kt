package com.apa.alumnidirectory.data.model.ui

import com.apa.alumnidirectory.data.enums.Sort

data class FilterState(
    val query: String = "",
    val country: String? = null,
    val state: String? = null,
    val year: String? = null,
    val techStack: String? = null,
    val sort: String = Sort.DEFAULT.value
)
