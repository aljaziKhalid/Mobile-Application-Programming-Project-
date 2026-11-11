
package com.mystiq.ui.screens.bodyshape

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Purple = Color(0xFF625879)
private val Background = Color(0xFFF1F1F1)

@Composable
fun BodyShapeScreen(
    uiState: BodyShapeUiState,
    onShapeSelected: (String) -> Unit,
    onDone: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(45.dp))

        Text(
            text = "Choose Your Body Shape",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Select the figure that best represents your body shape",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Slim", "Medium", "Curvy").forEach { shape ->

                val selected = uiState.selectedShape == shape

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(220.dp)
                        .background(
                            if (selected) Color(0xFFE4DFEC)
                            else Color.White,
                            RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            onShapeSelected(shape)
                        }
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "♙",
                        fontSize = 75.sp,
                        color = Purple
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = shape,
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onDone,
            enabled = uiState.selectedShape != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple,
                disabledContainerColor = Color.LightGray
            )
        ) {
            Text("Done", fontSize = 18.sp)
        }
    }
}
