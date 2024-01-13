package com.scrollz.partrecognizer.domain.model

data class BaseUrl(
    val domain: String = "",
    val port: String = ""
) {
    val url: String
        get() {
            val domain = domain.ifBlank { "0.0.0.0" }
            val port = if (port.isNotBlank()) ":$port" else ""
            return "http://$domain$port"
        }
}
