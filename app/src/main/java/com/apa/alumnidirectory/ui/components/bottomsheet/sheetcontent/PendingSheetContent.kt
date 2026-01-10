package com.apa.alumnidirectory.ui.components.bottomsheet.sheetcontent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PendingSheetContent(
    title: String,
    onSubmit: (String) -> Unit
) {
    var msg by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        TextField(
            value = msg,
            onValueChange = { msg = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(
                "Message"
            ) },
            minLines = 8,
            maxLines = 8,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            onClick = { onSubmit(msg) }
        ) {
            Text(
                "Submit",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}