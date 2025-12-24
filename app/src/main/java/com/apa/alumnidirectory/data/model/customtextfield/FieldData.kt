package com.apa.alumnidirectory.data.model.customtextfield

data class FieldData(
    val label: String,
    val value: String,
    val onValueChange: (String) -> Unit
)