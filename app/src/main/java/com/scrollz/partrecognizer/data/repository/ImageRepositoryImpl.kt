package com.scrollz.partrecognizer.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.common.internal.ImageConvertUtils
import com.scrollz.partrecognizer.domain.repository.ImageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ImageRepository {

    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    override suspend fun imageProxyToBase64(imageProxy: ImageProxy): Result<String> = runCatching {
        withContext(dispatcher) {
            val inputImage = InputImage.fromMediaImage(
                imageProxy.image!!,
                imageProxy.imageInfo.rotationDegrees
            )
            val bitmap = ImageConvertUtils.getInstance().getUpRightBitmap(inputImage)
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT) ?: throw Exception()
        }
    }

    override suspend fun saveImage(imageBase64: String, name: String): Result<String> {
        return withContext(dispatcher) {
            runCatching {
                val file = File(appContext.filesDir, name)
                FileOutputStream(file).use { it.write(Base64.decode(imageBase64, Base64.DEFAULT)) }
                file.path
            }
        }
    }

    override suspend fun deleteImage(imagePath: String): Result<Boolean> = runCatching {
        withContext(dispatcher) { File(imagePath).delete() }
    }

}
