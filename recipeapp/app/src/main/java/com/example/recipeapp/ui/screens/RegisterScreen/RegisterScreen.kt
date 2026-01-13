package com.example.recipeapp.ui.screens.RegisterScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipeapp.R
import com.example.recipeapp.ui.auth.AuthState
import com.example.recipeapp.ui.auth.AuthViewModel
import com.example.recipeapp.ui.auth.components.GoogleSignInButton

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    navigateToLogin: () -> Unit
){
    val state by authViewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "CADASTRE-SE",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 48.sp
        )

        Spacer(Modifier.height(32.dp))
        Image(
            painter = painterResource(R.drawable.ic_logo_icon),
            contentDescription = "Logo"
        )


        Spacer(Modifier.height(32.dp))

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var rememberMe by remember { mutableStateOf(false) }
        var viewPassword by remember {mutableStateOf(false)}
        var viewConfirmPassword by remember { mutableStateOf(false) }
        var passwordsMatch = password == confirmPassword || confirmPassword.isEmpty()

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold),
            trailingIcon = {
                Image(
                    painter = painterResource(R.drawable.ic_email),
                    contentDescription = "Ícone do email",
                    modifier = Modifier.graphicsLayer(
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary,
                            BlendMode.SrcIn)
                    )
                )
            }
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold),
            visualTransformation = if (viewPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (viewPassword) R.drawable.ic_eye_slash else R.drawable.ic_eye
                val description = if (viewPassword) "Esconder senha" else "Ver senha"

                Image(
                    painter = painterResource(image),
                    contentDescription = description,
                    modifier =  Modifier.clickable(onClick = {viewPassword = !viewPassword}).graphicsLayer(
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary,
                            BlendMode.SrcIn)
                    )
                )
            }
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar senha") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold),
            visualTransformation = if (viewConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (viewConfirmPassword) R.drawable.ic_eye_slash else R.drawable.ic_eye
                val description = if (viewConfirmPassword) "Esconder senha" else "Ver senha"

                Image(
                    painter = painterResource(image),
                    contentDescription = description,
                    modifier =  Modifier.clickable(onClick = {viewConfirmPassword = !viewConfirmPassword}).graphicsLayer(
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary,
                            BlendMode.SrcIn)
                    )
                )
            }
        )

        Spacer(Modifier.height(8.dp))

        if (!passwordsMatch) {
            Text(
                text = "As senhas são diferentes",
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { authViewModel.register(email, password, rememberMe) },
            modifier = Modifier.fillMaxWidth(),
            enabled = if(email == "" || password == "" || !passwordsMatch) false else true
        ) {
            if(state.loading){
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(25.dp)
                )
            } else {
                Text(
                    "CADASTRAR",
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(Modifier.height(12.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ou se cadastre com:",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(6.dp))

            GoogleSignInButton(
                onGoogleSignInCompleted = {idToken ->
                    authViewModel.loginWithGoogle(idToken)
                },
                onError = {errorMsg ->
                    println(errorMsg)
                }
            )

            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Já possui uma conta?",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 22.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(onClick = {navigateToLogin()}) {
                    Text(
                        text = "Entrar",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 22.sp
                    )
                }
            }
        }
        Spacer(Modifier.height(12.dp))

        if(state.error != null){
            AlertDialog(
                onDismissRequest = { authViewModel.clearMessage()},
                confirmButton = {
                    Button(onClick = { authViewModel.clearMessage() }){
                        Text("Ok")
                    }
                },
                title = { Text("Erro ao enviar e-mail.")},
                text = {Text(state.error!!)}
            )
        }

        if(state.success != null){
            AlertDialog(
                onDismissRequest = { authViewModel.clearMessage()},
                confirmButton = {
                    Button(onClick = { authViewModel.clearMessage() }){
                        Text("Ok")
                    }
                },
                title = { Text("Cadastro Realizado.")},
                text = {Text(state.success!!)}
            )
        }
    }
}