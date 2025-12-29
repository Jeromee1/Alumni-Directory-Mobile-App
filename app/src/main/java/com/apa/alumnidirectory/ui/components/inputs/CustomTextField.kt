package com.apa.alumnidirectory.ui.components.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apa.alumnidirectory.data.model.customtextfield.FieldData

@Composable
fun CustomTextField(
    field: FieldData
) {
    TextField(
        value = field.value,
        onValueChange = field.onValueChange,
        shape = RoundedCornerShape(8.dp),
        placeholder = { Text(
            field.label
        ) },
        modifier = Modifier.fillMaxWidth()
    )
}