package com.apa.alumnidirectory.data.model.ui

data class Country(
    val name: String,
    val states: List<State> = emptyList()
)
