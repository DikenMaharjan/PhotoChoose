package com.example.photochoose.data.photo

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.example.photochoose.core.model.AppDispatchers
import com.example.photochoose.core.utils.permission.PermissionInspector
import com.example.photochoose.data.photo.model.ImageInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val dispatchers: AppDispatchers,
    private val permissionInspector: PermissionInspector
) {

    suspend fun getImages(): List<ImageInfo> {
        return if (!permissionInspector.photosGranted) {
            listOf()
        } else {
            withContext(dispatchers.background) {
                val columns = arrayOf(
                    MediaStore.Images.ImageColumns._ID,
                    MediaStore.Images.ImageColumns.TITLE,
                    MediaStore.Images.ImageColumns.DISPLAY_NAME
                )

                appContext.contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    columns,
                    null,
                    null,
                    "${MediaStore.Images.ImageColumns.DATE_ADDED} DESC",
                )?.use { cursor ->
                    mutableListOf<ImageInfo>().apply {
                        while (cursor.moveToNext()) {
                            add(getImageFromCursor(cursor))
                        }
                    }
                } ?: emptyList()
            }
        }
    }

    private fun Cursor.contains(columnIndex: Int): Boolean {
        return columnIndex != -1 && !this.isNull(columnIndex)
    }


    private fun getImageFromCursor(cursor: Cursor): ImageInfo {

        val titleIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.TITLE)
        val displayNameIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)

        val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID))

        val title = if (cursor.contains(titleIndex)) {
            cursor.getString(titleIndex)
        } else {
            null
        }

        val displayName = if (cursor.contains(displayNameIndex)) {
            cursor.getString(displayNameIndex)
        } else {
            null
        }


        return ImageInfo(
            uri = getImageContentUri(id),
            title = title,
            displayName = displayName
        )
    }

    private fun getImageContentUri(id: Long): Uri {
        return ContentUris.withAppendedId(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            id
        )
    }


}

