package com.scrollz.partrecognizer.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Request(
    @Json(name = "photoBase64")
    val photoBase64: String
)
