package com.example.imagefilters.repositories

import android.graphics.Bitmap
import android.net.Uri

interface EditImageRepository {
    suspend fun prepareImagePreview(imageUri : Uri) : Bitmap?

}