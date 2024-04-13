package com.example.photochoose.feature.choose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.photochoose.core.utils.extensionfunctions.openPackageSettings
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ReadPermissionNotGrantedView(
    modifier: Modifier = Modifier,
    permissionState: PermissionState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PermissionRequiredView(
            onGivePermissionClicked = permissionState::launchPermissionRequest,
            permissionState = permissionState
        )
    }
    LaunchedEffect(permissionState) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequiredView(
    modifier: Modifier = Modifier,
    onGivePermissionClicked: () -> Unit,
    permissionState: PermissionState
) {
    val context = LocalContext.current
    if (permissionState.status is PermissionStatus.Denied) {
        val shouldShowRationale = permissionState.status.shouldShowRationale
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Permission Required",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "We need read permissions to access photos on this device.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (shouldShowRationale) {
                        onGivePermissionClicked()
                    } else {
                        context.openPackageSettings()
                    }
                }
            ) {
                Text(text = "Give Permission")
            }
        }
    }
}