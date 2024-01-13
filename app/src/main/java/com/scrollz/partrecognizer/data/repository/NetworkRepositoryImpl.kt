package com.scrollz.partrecognizer.data.repository

import com.scrollz.partrecognizer.data.remote.Api
import com.scrollz.partrecognizer.domain.model.Request
import com.scrollz.partrecognizer.domain.model.Response
import com.scrollz.partrecognizer.domain.repository.NetworkRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val api: Api,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): NetworkRepository {

    override suspend fun analyzeImage(base64Image: String): Result<Response> = runCatching {
        withContext(dispatcher) { api.analyzeImage(Request(base64Image)) }
    }

}
