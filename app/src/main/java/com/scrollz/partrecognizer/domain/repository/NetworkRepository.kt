package com.scrollz.partrecognizer.domain.repository

import com.scrollz.partrecognizer.domain.model.Response

interface NetworkRepository {

    suspend fun analyzeImage(base64Image: String): Result<Response>

}
