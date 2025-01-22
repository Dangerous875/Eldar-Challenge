package com.barzabaldevs.eldarwallet.ui.screens.loginScreen

import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.barzabaldevs.eldarwallet.R
import com.barzabaldevs.eldarwallet.ui.core.theme.Background
import com.barzabaldevs.eldarwallet.ui.core.theme.Primary
import com.barzabaldevs.eldarwallet.ui.core.theme.Secondary
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(loginForm: Boolean, auth: FirebaseAuth, navigateToMainScreen: () -> Unit) {
    val context = LocalContext.current
    val showLoginForm =
        rememberSaveable {
            mutableStateOf(loginForm)
        }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Background, Primary),
                        startY = 0f,
                        endY = 600f,
                    ),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.size(5.dp))
            Image(
                modifier = Modifier.padding(16.dp),
                painter = painterResource(id = R.drawable.eldar_title),
                contentDescription = "Eldar Logo",
            )

            if (showLoginForm.value) {
                Text(
                    text = "Log In",
                    fontWeight = FontWeight.Bold,
                    color = Secondary,
                )
                UserFormLogin(
                    isCreateAccount = false,
                ) { email, password ->
                    loginUser(auth, email, password, context) { navigateToMainScreen() }
                }
            } else {
                Text(
                    text = "Create Account",
                    fontWeight = FontWeight.Bold,
                    color = Secondary,
                )
                UserFormCreateAccount(isCreateAccount = true) { email, password, name, lastName ->
                    createUser(
                        auth,
                        email,
                        password,
                        name,
                        lastName,
                        context
                    ) { navigateToMainScreen() }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val textQuestion =
                    if (showLoginForm.value) "Don't have an account yet?" else "Already have an account?"
                val textAnswer = if (showLoginForm.value) "Create an Account" else "Log In"
                Text(text = textQuestion)
                Text(
                    text = textAnswer,
                    modifier =
                    Modifier
                        .clickable {
                            showLoginForm.value = !showLoginForm.value
                        }
                        .padding(start = 5.dp),
                    color = Secondary,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
fun UserFormLogin(
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit,
) {
    val email =
        rememberSaveable {
            mutableStateOf("")
        }
    val password =
        rememberSaveable {
            mutableStateOf("")
        }
    val passwordVisible =
        rememberSaveable {
            mutableStateOf(false)
        }
    val validValue =
        remember(email.value, password.value) {
            email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
        }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        EmailInput(emailState = email)
        PasswordInput(
            passwordState = password,
            labelId = "Password",
            passwordVisible = passwordVisible,
        )
        SubmitButton(
            textId = if (isCreateAccount) "Create Account" else "Login",
            validInput = validValue,
        ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}

@Composable
fun UserFormCreateAccount(
    isCreateAccount: Boolean = false,
    onDone: (String, String, String, String) -> Unit,
) {
    val email =
        rememberSaveable {
            mutableStateOf("")
        }
    val password =
        rememberSaveable {
            mutableStateOf("")
        }
    val name =
        rememberSaveable {
            mutableStateOf("")
        }
    val lastName =
        rememberSaveable {
            mutableStateOf("")
        }
    val passwordVisible =
        rememberSaveable {
            mutableStateOf(false)
        }
    val validValue =
        remember(email.value, password.value, name.value, lastName.value) {
            email.value.trim().isNotEmpty() &&
                    password.value.trim().isNotEmpty() &&
                    name.value.trim().isNotEmpty() &&
                    lastName.value.trim().isNotEmpty()
        }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        EmailInput(emailState = email)
        PasswordInput(
            passwordState = password,
            labelId = "Password (6 digits)",
            passwordVisible = passwordVisible,
        )
        NameInput(nameState = name)
        LastnameInput(lastnameState = lastName)
        SubmitButton(
            textId = if (isCreateAccount) "Create Account" else "Login",
            validInput = validValue,
        ) {
            onDone(
                email.value.trim(),
                password.value.trim(),
                name.value.trim(),
                lastName.value.trim(),
            )
            keyboardController?.hide()
        }
    }
}

@Composable
fun SubmitButton(
    textId: String,
    validInput: Boolean,
    onUserClick: () -> Unit,
) {
    Button(
        onClick = onUserClick,
        modifier =
        Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape,
        enabled = validInput,
    ) {
        Text(
            text = textId,
            modifier = Modifier.padding(5.dp),
        )
    }
}

@Composable
fun PasswordInput(
    passwordState: MutableState<String>,
    labelId: String,
    passwordVisible: MutableState<Boolean>,
) {
    val visualTransformation =
        if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(text = labelId) },
        singleLine = true,
        modifier =
        Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (passwordState.value.isNotBlank()) {
                PasswordVisibleIcon(passwordVisible)
            }
        },
    )
}

@Composable
fun PasswordVisibleIcon(passwordVisible: MutableState<Boolean>) {
    val image = if (passwordVisible.value) Icons.Default.VisibilityOff else Icons.Default.Visibility
    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
        Icon(imageVector = image, contentDescription = "ocultar/desocultar")
    }
}

@Composable
fun EmailInput(
    emailState: MutableState<String>,
    labelId: String = "Email",
) {
    InputFieldCom(
        valueState = emailState,
        labelId = labelId,
        keyboardType = KeyboardType.Email,
    )
}

@Composable
fun NameInput(
    nameState: MutableState<String>,
    labelId: String = "Name",
) {
    InputFieldCom(
        valueState = nameState,
        labelId = labelId,
        keyboardType = KeyboardType.Email,
    )
}

@Composable
fun LastnameInput(
    lastnameState: MutableState<String>,
    labelId: String = "Last name",
) {
    InputFieldCom(
        valueState = lastnameState,
        labelId = labelId,
        keyboardType = KeyboardType.Email,
    )
}

@Composable
fun InputFieldCom(
    valueState: MutableState<String>,
    labelId: String,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType,
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        modifier =
        Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions =
        KeyboardOptions(
            keyboardType = keyboardType,
        ),
    )
}

fun loginUser(
    auth: FirebaseAuth,
    email: String,
    password: String,
    context: Context,
    navigateToMainScreen: () -> Unit
) {
    if (email.isNotBlank() && password.isNotBlank()) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast
                    .makeText(context, "Login Successful", Toast.LENGTH_SHORT)
                    .show()
                navigateToMainScreen()
            } else {
                Toast
                    .makeText(context, "User or Password incorrect", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    } else {
        Toast.makeText(context, "User or Password incorrect", Toast.LENGTH_SHORT).show()
    }
}

fun createUser(
    auth: FirebaseAuth,
    email: String,
    password: String,
    name: String,
    lastName: String,
    context: Context,
    navigateToMainScreen: () -> Unit
) {
    auth
        .createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
//                val userData =
//                    UserData(
//                        userID = userId,
//                        name = name,
//                        nickname = nickname,
//                        email = email,
//                        infoUser = "Info Default",
//                    )
                Toast
                    .makeText(context, "User Created Successfully", Toast.LENGTH_SHORT)
                    .show()
                navigateToMainScreen()
            } else if (checkValidEmail(email) && checkValidPassword(password)) {
                Toast
                    .makeText(context, "Account Already Exists", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast
                    .makeText(context, "Please check your email and password", Toast.LENGTH_SHORT)
                    .show()
            }
        }
}

fun checkValidPassword(password: String): Boolean {
    return password.length >= 6
}

fun checkValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
