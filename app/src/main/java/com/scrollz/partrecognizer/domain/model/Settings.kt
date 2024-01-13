package com.scrollz.partrecognizer.domain.model

data class Settings(
    val theme: Theme = Theme.System,
    val baseUrl: BaseUrl = BaseUrl()
)
