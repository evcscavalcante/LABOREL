package com.example.laborel.Compactação.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.laborel.Compactação.Compactacao
import com.example.laborel.Compactação.models.FormViewModel

@Composable
fun EnsaioContent(viewModel: FormViewModel) {
    var selectedCylinder by remember { mutableStateOf(0) }
    val cylinders = listOf("CL01", "CL02", "CL03", "CL04", "CL05")

    Column {
        TabRow(selectedTabIndex = selectedCylinder) {
            cylinders.forEachIndexed { index, title ->
                Tab(
                    selected = selectedCylinder == index,
                    onClick = { selectedCylinder = index },
                    text = { Text(title) }
                )
            }
        }

        CylinderContent(
            "Cilindro ${selectedCylinder + 1}",
            viewModel.cylinderStates[selectedCylinder],
            viewModel
        )
    }
}
