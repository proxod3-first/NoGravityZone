package com.proxod3.nogravityzone.ui.screens.signup


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proxod3.nogravityzone.R
import com.proxod3.nogravityzone.ui.shared_components.CustomPasswordTextField
import com.proxod3.nogravityzone.ui.shared_components.CustomTextField
import com.proxod3.nogravityzone.ui.shared_components.LoadingIndicator
import com.proxod3.nogravityzone.utils.Utils.showToast


@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    navigateToSignIn: () -> Unit
) {

    val viewModel: SignUpViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState().value
    val signUpFormData = viewModel.signUpFormData.collectAsState().value

    val context = LocalContext.current

    SignUpScreenContent(
        signUpFormData,
        onCreateAccount = { ->
            viewModel.createAccount()
        },
        onSignInClick = navigateToSignIn,
        onUpdateEmail = { email -> viewModel.updateEmail(email) },
        onUpdatePassword = { password -> viewModel.updatePassword(password) },
        onUpdateDisplayName = { username -> viewModel.updateDisplayName(username) },
    )

    if (uiState is SignUpUiState.Loading) {
        LoadingIndicator()
    } else if (uiState is SignUpUiState.Error) {
        val errorMessage = stringResource(uiState.messageStringResource)
        showToast(
            context,
            errorMessage,
        )
    } else if (uiState is SignUpUiState.Success) {
        onSignUpSuccess()
    }
}

@Composable
fun SignUpScreenContent(
    signUpFormData: SignUpFormData,
    onCreateAccount: () -> Unit,
    onUpdateDisplayName: (String) -> Unit,
    onUpdateEmail: (String) -> Unit,
    onUpdatePassword: (String) -> Unit,
    onSignInClick: () -> Unit,
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.create_account),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.welcome_to_nogravityzone_where_you_can_learn_share_and_train_together),
                color = Color.Gray,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(32.dp))


            CustomTextField(
                text = signUpFormData.displayName, label = stringResource(R.string.display_name),
                isError = !signUpFormData.isUsernameValid,
                errorText = stringResource(R.string.display_cannot_be_empty),
                onValueChange = onUpdateDisplayName,
            )


            CustomTextField(
                text = signUpFormData.email, label = stringResource(R.string.email),
                isError = !signUpFormData.isEmailValid,
                errorText = stringResource(R.string.email_invalid_format),
                options = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = onUpdateEmail,
            )



            CustomPasswordTextField(
                text = signUpFormData.password, label = stringResource(R.string.password),
                isError = !signUpFormData.isPasswordValid,
                errorText = stringResource(R.string.password_must_be_atleast_six_characters_long),
                onValueChange = onUpdatePassword,
            )


            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.been_here_before),
                    fontSize = 14.sp,
                )
                TextButton(onClick = onSignInClick) {
                    Text(text = stringResource(R.string.sign_in_here))
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { onCreateAccount() },
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = stringResource(R.string.create_account),
                    fontSize = 24.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
}