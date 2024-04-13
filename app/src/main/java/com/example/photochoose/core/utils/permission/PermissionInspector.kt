package com.example.photochoose.core.utils.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PermissionInspector @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val photosGranted: Boolean
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            hasPermission(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            hasPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

    private fun hasPermission(permission: String) = (ActivityCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED)

}
