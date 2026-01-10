package com.apa.alumnidirectory.data.model.ui

data class FilterOptions(
    val countries: List<String> = emptyList(),
    val states: List<String> = emptyList(),
    val years: List<String> = emptyList(),
    val techStacks: List<String> = emptyList(),
    val status: List<String> = emptyList()
)
