package com.apa.alumnidirectory.data.model.ui

data class FieldData(
    val label: String,
    val value: String,
    val list: List<String> = emptyList(),
    val isPassword: Boolean = false,
    val onValueChange: (String) -> Unit
)