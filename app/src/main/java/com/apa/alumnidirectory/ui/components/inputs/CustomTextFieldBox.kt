package com.apa.alumnidirectory.ui.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apa.alumnidirectory.data.model.customtextfield.FieldData
import com.apa.alumnidirectory.ui.theme.SecondaryG

@Composable
fun CustomTextFieldBox(
    categoryName: String,
    fields: List<FieldData>
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(28.dp, 16.dp)
            .shadow(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(SecondaryG, RoundedCornerShape(12.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = categoryName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            fields.forEach { field ->
                CustomTextField(field)
            }
        }
    }
}