package com.example.coretrip.feature.auth.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.coretrip.R

/**
 * Reusable control for toggling password field visibility.
 *
 * The component reports the requested state change through [onClick] and
 * does not own the visibility state itself.
 */
@Composable
fun PasswordVisibilityButton(
    visible: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector =
                if (visible) {
                    Icons.Default.VisibilityOff
                } else {
                    Icons.Default.Visibility
                },
            contentDescription = stringResource(
                R.string.toggle_password_visibility
            )
        )
    }
}