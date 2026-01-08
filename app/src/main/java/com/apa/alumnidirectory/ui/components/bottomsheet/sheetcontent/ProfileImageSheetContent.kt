package com.apa.alumnidirectory.ui.components.bottomsheet.sheetcontent

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.apa.alumnidirectory.ui.components.pfp.PfpBox

@Composable
fun ProfileImageSheetContent(
    selectedPfp: Int,
    onSelectedChanged: (Int) -> Unit
) {
    val items = listOf(0, 1, 2)
    var selectedPfp by remember { mutableIntStateOf(selectedPfp) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items) { pfpId ->
            PfpBox(pfpId, selectedPfp) {
                selectedPfp = pfpId
                onSelectedChanged(pfpId)
            }
        }
    }
}