package com.example.registrationform

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.registrationform.ui.theme.RegistrationFormTheme
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.isSystemInDarkTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistrationFormTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
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

    val genders = listOf("Male", "Female")

    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var selectedGender by rememberSaveable { mutableStateOf(genders.first()) }
    var agreed by rememberSaveable { mutableStateOf(false) }

    var hidePassword by rememberSaveable { mutableStateOf(true) }
    val passwordVisual: VisualTransformation =
        if (hidePassword) PasswordVisualTransformation() else VisualTransformation.None

    val isDark = isSystemInDarkTheme()

    val gradient = Brush.verticalGradient(
        colorStops = arrayOf(
            0.0f  to MaterialTheme.colorScheme.primary.copy(alpha = if (isDark) 0.62f else 0.30f),
            0.55f to MaterialTheme.colorScheme.secondaryContainer.copy(alpha = if (isDark) 0.48f else 0.20f),
            1.0f  to MaterialTheme.colorScheme.background

        )
    )

    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Create Account",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 60.dp, bottom = 20.dp)
            )

            // First Last Name
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(12.dp))

            // Username
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            // Gender
            Spacer(Modifier.height(16.dp))
            Text("Gender", fontWeight = FontWeight.SemiBold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = selectedGender == "Male", onClick = { selectedGender = "Male" })
                Text("Male")
                Spacer(Modifier.width(8.dp))
                RadioButton(selected = selectedGender == "Female", onClick = { selectedGender = "Female" })
                Text("Female")
            }

            // Agreement
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Checkbox(checked = agreed, onCheckedChange = { agreed = it })
                Text(
                    text = "I agree to the Terms and Conditions.",
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            // Buttons
            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        // reset
                        firstName = ""
                        lastName = ""
                        username = ""
                        password = ""
                        selectedGender = genders.first()
                        agreed = false
                        Toast.makeText(context, "Form cleared", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text("Clear")
                }

                Button(
                    onClick = {
                        val fullName = listOf(firstName, lastName).filter { it.isNotBlank() }.joinToString(" ")
                        val safeName = if (fullName.isBlank()) "(no name)" else fullName
                        val safeUser = if (username.isBlank()) "(no username)" else username

                        Toast.makeText(
                            context,
                            "Name: $safeName\nUsername: $safeUser\nGender: $selectedGender",
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    Text("Submit")
                }
            }
        }
    }
}


@Preview(showBackground = true, name = "Light")
@Composable
fun RegistrationPreviewLight() {
    RegistrationFormTheme(dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RegistrationPage()
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun RegistrationPreviewDark() {
    RegistrationFormTheme(dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RegistrationPage()
        }
    }
}
