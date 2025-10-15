package com.example.registrationform

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.registrationform.ui.theme.RegistrationFormTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistrationFormTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFEEEEEE)
                ) {
                    RegistrationPage()
                }
            }
        }
    }
}

@Composable
fun RegistrationPage(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") } // starts empty, recompose when changed
    var email by remember { mutableStateOf("") } // same idea for email
    val genders = listOf("Male", "Female") // static list (not state)
    var selectedGender by remember { mutableStateOf(genders.first()) } // "Male" initially
    var agreed by remember { mutableStateOf(false) } // checkbox unchecked initially

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Registration Form",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 60.dp, bottom = 20.dp)
        )

        // Name field
        OutlinedTextField(
            value = name,
            label = { Text("Name") },
            onValueChange = { name = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
// Email field
        OutlinedTextField(
            value = email,
            label = { Text("Email") },
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Gender selection
        Text(
            text = "Gender",
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
        )
        genders.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedGender == option,
                    onClick = { selectedGender = option },
                    modifier = Modifier
                        .height(35.dp)
                )
                Text(option, modifier = Modifier.padding(start = 4.dp))
            }
        }
        // Check box agreement
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 50.dp)
        ){
            Checkbox(checked = agreed, onCheckedChange = { agreed = it })
            Text(
                text = "I agree to the terms and conditions",
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        // Submit button After the “submit” button clicked.
        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    Toast.makeText(
                        context.applicationContext,
                        "Thank you for registering!",
                        Toast.LENGTH_LONG
                    ).show()
                },
                modifier = Modifier
                    .width(160.dp) // smaller fixed width
                    .height(44.dp), // optional: make it a bit shorter
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text("Submit")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegistrationPreview() {
    RegistrationFormTheme {
        RegistrationPage()
    }
}
