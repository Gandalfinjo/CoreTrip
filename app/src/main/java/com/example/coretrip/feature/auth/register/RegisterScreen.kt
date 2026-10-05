package com.example.coretrip.feature.auth.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coretrip.R
import com.example.coretrip.core.ui.theme.CoreTripTheme
import com.example.coretrip.feature.auth.components.AuthField
import com.example.coretrip.feature.auth.components.PasswordVisibilityButton

/**
 * Renders the registration form from [uiState].
 *
 * The screen owns only presentation-specific UI state, such as password
 * visibility. Form values, validation results, and registration state are
 * supplied externally so the composable remains independent of the ViewModel.
 *
 * @param uiState Current registration form and operation state.
 * @param onFirstNameChanged Called when the first name changes.
 * @param onLastNameChanged Called when the last name changes.
 * @param onEmailChanged Called when the email changes.
 * @param onUsernameChanged Called when the username changes.
 * @param onPasswordChanged Called when the password changes.
 * @param onConfirmPasswordChanged Called when password confirmation changes.
 * @param onRegisterClick Called when the registration form is submitted.
 * @param onLoginClick Called when the user chooses to return to log-in.
 * @param onErrorDismiss Called when a generic registration error is dismissed.
 */
@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    onErrorDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var confirmPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(
                    BorderStroke(
                        width = 0.5.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    ),
                    RoundedCornerShape(18.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.mipmap.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 32.sp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.tertiary
                    )
                )
            )
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.create_your_account),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(32.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            border = BorderStroke(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.personal_info),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AuthField(
                        label = stringResource(R.string.first_name),
                        value = uiState.firstName,
                        onValueChange = onFirstNameChanged,
                        placeholder = stringResource(R.string.first_name),
                        modifier = Modifier.weight(1f),
                        error = uiState.firstNameError?.let {
                            stringResource(it.stringRes)
                        },
                        imeAction = ImeAction.Next
                    )

                    AuthField(
                        label = stringResource(R.string.last_name),
                        value = uiState.lastName,
                        onValueChange = onLastNameChanged,
                        placeholder = stringResource(R.string.last_name),
                        modifier = Modifier.weight(1f),
                        error = uiState.lastNameError?.let {
                            stringResource(it.stringRes)
                        },
                        imeAction = ImeAction.Next
                    )
                }

                AuthField(
                    label = stringResource(R.string.email),
                    value = uiState.email,
                    onValueChange = onEmailChanged,
                    placeholder = stringResource(R.string.you_example_com),
                    error = uiState.emailError?.let { error ->
                        stringResource(error.stringRes)
                    },
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            border = BorderStroke(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.account_info),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                AuthField(
                    label = stringResource(R.string.username),
                    value = uiState.username,
                    onValueChange = onUsernameChanged,
                    placeholder = stringResource(R.string.choose_a_username),
                    error = uiState.usernameError?.let { error ->
                        stringResource(error.stringRes)
                    },
                    imeAction = ImeAction.Next
                )

                AuthField(
                    label = stringResource(R.string.password),
                    value = uiState.password,
                    onValueChange = onPasswordChanged,
                    placeholder = stringResource(R.string.at_least_6_characters),
                    error = uiState.passwordError?.let { error ->
                        stringResource(error.stringRes)
                    },
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                    visualTransformation =
                        if (passwordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                    trailingIcon = {
                        PasswordVisibilityButton(
                            visible = passwordVisible,
                            onClick = {
                                passwordVisible = !passwordVisible
                            }
                        )
                    }
                )

                AuthField(
                    label = stringResource(R.string.confirm_password),
                    value = uiState.confirmPassword,
                    onValueChange = onConfirmPasswordChanged,
                    placeholder = stringResource(R.string.repeat_your_password),
                    error = uiState.confirmPasswordError?.let { error ->
                        stringResource(error.stringRes)
                    },
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    visualTransformation =
                        if (confirmPasswordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                    trailingIcon = {
                        PasswordVisibilityButton(
                            visible = confirmPasswordVisible,
                            onClick = {
                                confirmPasswordVisible = !confirmPasswordVisible
                            }
                        )
                    }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onRegisterClick,
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = stringResource(R.string.register),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = onLoginClick) {
            Text(
                text = stringResource(R.string.already_have_an_account_login),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(Modifier.height(48.dp))
    }

    uiState.error?.let {
        AlertDialog(
            onDismissRequest = onErrorDismiss,
            confirmButton = {
                TextButton(onClick = onErrorDismiss) {
                    Text(stringResource(R.string.ok))
                }
            },
            title = {
                Text(text = stringResource(R.string.registration))
            },
            text = {
                Text(text = stringResource(R.string.something_went_wrong))
            }
        )
    }
}

private val RegisterFieldError.stringRes: Int
    get() = when (this) {
        RegisterFieldError.FirstNameRequired ->
            R.string.first_name_is_required

        RegisterFieldError.LastNameRequired ->
            R.string.last_name_is_required

        RegisterFieldError.EmailRequired ->
            R.string.email_is_required

        RegisterFieldError.InvalidEmail ->
            R.string.invalid_email_format

        RegisterFieldError.UsernameRequired ->
            R.string.username_required

        RegisterFieldError.PasswordRequired ->
            R.string.password_required

        RegisterFieldError.PasswordTooShort ->
            R.string.password_must_be_at_least_6_characters

        RegisterFieldError.ConfirmPasswordRequired ->
            R.string.please_confirm_your_password

        RegisterFieldError.PasswordsDoNotMatch ->
            R.string.passwords_do_not_match

        RegisterFieldError.UsernameAlreadyExists ->
            R.string.username_already_exists

        RegisterFieldError.EmailAlreadyExists ->
            R.string.email_is_already_in_use
    }

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    CoreTripTheme {
        RegisterScreen(
            uiState = RegisterUiState(),
            onFirstNameChanged = {},
            onLastNameChanged = {},
            onEmailChanged = {},
            onUsernameChanged = {},
            onPasswordChanged = {},
            onConfirmPasswordChanged = {},
            onRegisterClick = {},
            onLoginClick = {},
            onErrorDismiss = {}
        )
    }
}