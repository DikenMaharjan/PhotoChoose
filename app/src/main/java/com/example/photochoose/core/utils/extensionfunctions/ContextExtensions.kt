package com.example.photochoose.core.utils.extensionfunctions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Context.openPackageSettings() {
    val contextIntent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", this.packageName, null)
    )
    startActivity(contextIntent)
}
