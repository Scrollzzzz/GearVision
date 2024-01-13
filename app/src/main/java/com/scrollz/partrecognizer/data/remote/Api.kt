package com.scrollz.partrecognizer.data.remote

import com.scrollz.partrecognizer.domain.model.Response
import com.scrollz.partrecognizer.domain.model.Request
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("/api/recognizer")
    suspend fun analyzeImage(
        @Body base64Image: Request
    ): Response

}
