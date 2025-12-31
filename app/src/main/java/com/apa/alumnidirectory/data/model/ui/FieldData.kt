package com.apa.alumnidirectory.data.model.ui

data class FieldData(
    val label: String,
    val value: String,
    val onValueChange: (String) -> Unit
)