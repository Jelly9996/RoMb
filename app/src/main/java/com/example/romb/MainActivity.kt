package com.example.romb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.romb.ui.theme.RoMbTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoMbTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RhombusAreaScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun RhombusAreaScreen(modifier: Modifier = Modifier) {
    var variantText by remember { mutableStateOf("") }
    var sideText by remember { mutableStateOf("") }
    var heightText by remember { mutableStateOf("") }
    var d1Text by remember { mutableStateOf("") }
    var d2Text by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = "Площадь ромба",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "1. По стороне и высоте (S = a·h)\n2. По диагоналям (S = d1·d2/2)",
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = variantText,
            onValueChange = {
                variantText = it
                result = null
                errorMessage = null
            },
            label = { Text("Номер варианта решения (1 или 2)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        val variant = variantText.toIntOrNull()

        when (variant) {
            1 -> {
                OutlinedTextField(
                    value = sideText,
                    onValueChange = { sideText = it },
                    label = { Text("Сторона a") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = heightText,
                    onValueChange = { heightText = it },
                    label = { Text("Высота h") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            2 -> {
                OutlinedTextField(
                    value = d1Text,
                    onValueChange = { d1Text = it },
                    label = { Text("Диагональ d1") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = d2Text,
                    onValueChange = { d2Text = it },
                    label = { Text("Диагональ d2") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            else -> {
                if (variantText.isNotEmpty()) {
                    Text(
                        text = "Введите 1 или 2",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                errorMessage = null
                result = null
                try {
                    val area: Double = when (variant) {
                        1 -> {
                            val a = sideText.replace(",", ".").toDouble()
                            val h = heightText.replace(",", ".").toDouble()
                            if (a <= 0 || h <= 0) {
                                throw IllegalArgumentException("Значения должны быть положительными")
                            }
                            a * h
                        }
                        2 -> {
                            val d1 = d1Text.replace(",", ".").toDouble()
                            val d2 = d2Text.replace(",", ".").toDouble()
                            if (d1 <= 0 || d2 <= 0) {
                                throw IllegalArgumentException("Значения должны быть положительными")
                            }
                            (d1 * d2) / 2.0
                        }
                        else -> {
                            throw IllegalArgumentException("Неверный номер варианта. Введите 1 или 2")
                        }
                    }
                    result = "Площадь ромба: %.4f".format(area)
                } catch (_: NumberFormatException) {
                    errorMessage = "Введите корректные числовые значения"
                } catch (e: IllegalArgumentException) {
                    errorMessage = e.message
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Вычислить")
        }

        Spacer(modifier = Modifier.height(20.dp))

        result?.let {
            Text(
                text = it,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        errorMessage?.let {
            Text(
                text = it,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRhombusAreaScreen() {
    RoMbTheme {
        RhombusAreaScreen()
    }
}