package com.example.loginjetpackcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseAuthApp()
        }
    }
}

@Composable
fun FirebaseAuthApp() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Firebase Auth App") })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    registerUser(auth, email, password)
                }) {
                    Text("Register")
                }
                Button(onClick = {
                    loginUser(auth, email, password)
                }) {
                    Text("Login")
                }
            }
        }
    }
}

fun registerUser(auth: FirebaseAuth, email: String, password: String) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        auth.app.applicationContext,
                        "User Registered Successfully",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        auth.app.applicationContext,
                        task.exception?.message ?: "Registration Failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}

fun loginUser(auth: FirebaseAuth, email: String, password: String) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        auth.app.applicationContext,
                        "Login Successful",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        auth.app.applicationContext,
                        task.exception?.message ?: "Login Failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FirebaseAuthApp()
}