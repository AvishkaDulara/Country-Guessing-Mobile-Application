package com.example.mobilecw

import GuessCountry
import android.content.Intent
import android.os.Bundle
//import android.text.style.BackgroundColorSpan
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
//import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    text = "Guess Country",
                    onClick = {
                        val intent = Intent(this@MainActivity, GuessCountry()::class.java)
                        startActivity(intent)
                    }
                )

                Button(
                    text = "Guess Hints",
                    onClick = {
                        val intent = Intent(this@MainActivity, GuessFlag::class.java)
                        startActivity(intent)
                    }
                )
                Button(
                    text = "Guess Flag",
                    onClick = {
                        val intent = Intent(this@MainActivity, GuessFlag::class.java)
                        startActivity(intent)
                    }
                )
                Button(
                    text = "Advanced Level",
                    onClick = {
                        val intent = Intent(this@MainActivity, AdvancedLevel::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }
    @Composable
    fun Button(text: String, onClick: @Composable () -> Unit) {
        TextButton(
            onClick = onClick,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(horizontal = 20.dp)
                .fillMaxWidth()

        ) {
            Text(
                text = text,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
