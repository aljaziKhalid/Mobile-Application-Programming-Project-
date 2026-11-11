
package com.mystiq.ui.screens.gender

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
fun GenderScreen(
    uiState: GenderUiState,
    onGenderSelected: (String) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(45.dp))

        Text(
            text = "Choose Your Gender",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "This helps us create a better outfit experience for you",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(35.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listOf("Female", "Male").forEach { gender ->

                val selected = uiState.selectedGender == gender

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(220.dp)
                        .background(
                            if (selected) Color(0xFFE4DFEC)
                            else Color.White,
                            RoundedCornerShape(18.dp)
                        )
                        .clickable {
                            onGenderSelected(gender)
                        }
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (gender == "Female") "♀" else "♂",
                        fontSize = 85.sp,
                        color = Purple
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = gender,
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onNext,
            enabled = uiState.selectedGender != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple,
                disabledContainerColor = Color.LightGray
            )
        ) {
            Text("Next", fontSize = 18.sp)
        }
    }
}
