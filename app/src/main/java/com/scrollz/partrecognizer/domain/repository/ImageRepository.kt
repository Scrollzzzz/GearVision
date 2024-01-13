package com.scrollz.partrecognizer.domain.repository

import androidx.camera.core.ImageProxy

interface ImageRepository {

    suspend fun imageProxyToBase64(imageProxy: ImageProxy): Result<String>

    suspend fun saveImage(imageBase64: String, name: String): Result<String>

    suspend fun deleteImage(imagePath: String): Result<Boolean>

}
