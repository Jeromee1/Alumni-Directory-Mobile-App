package com.apa.alumnidirectory.data.model.ui

data class DropdownData(
    val list: List<String>,
    val selectedItem: String,
    val onSelected: (String) -> Unit
)
