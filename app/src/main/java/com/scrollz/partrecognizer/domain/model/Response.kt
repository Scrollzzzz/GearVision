package com.scrollz.partrecognizer.domain.model

import androidx.compose.runtime.Immutable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Immutable
@JsonClass(generateAdapter = true)
data class Response(
    @Json(name = "tagged_image_base64")
    val resultImage: String,
    @Json(name = "recognized_parts")
    val detectedDetails: List<String>,
    @Json(name = "datetime")
    val dateTime: String
)
