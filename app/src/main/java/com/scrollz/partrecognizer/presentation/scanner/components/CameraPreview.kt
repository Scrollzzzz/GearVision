package com.scrollz.partrecognizer.presentation.scanner.components

import android.util.Log
import android.util.Size
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.util.concurrent.Executors

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    isTorchEnabled: Boolean,
    analyzeImage: (ImageProxy) -> Unit,
    closeScanner: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    val previewView = remember { PreviewView(context) }
    val camera = remember { mutableStateOf<Camera?>(null) }

    remember {
        ProcessCameraProvider.getInstance(context).apply {
            addListener({
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                val cameraProvider = get()

                val preview = Preview.Builder()
                    .build()
                    .also { it.setSurfaceProvider(previewView.surfaceProvider) }

                val resolutionSelector = ResolutionSelector.Builder()
                    .setResolutionStrategy(ResolutionStrategy.HIGHEST_AVAILABLE_STRATEGY)
                    .setResolutionFilter { _, _ -> listOf(Size(1200, 1200)) }
                    .build()

                val detailAnalysis = ImageAnalysis.Builder()
                    .setResolutionSelector(resolutionSelector)
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor) { imageProxy ->
                            analyzeImage(imageProxy)
                        }
                    }

                try {
                    cameraProvider.unbindAll()
                    camera.value = cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        detailAnalysis
                    )
                } catch (e: Exception) {
                    Log.e("CameraX", "bind error")
                    closeScanner()
                }
            }, ContextCompat.getMainExecutor(context))
        }
    }

    LaunchedEffect(isTorchEnabled) {
        camera.value?.cameraControl?.enableTorch(isTorchEnabled)
    }

    AndroidView(
        modifier = modifier,
        factory = { previewView }
    )
}
