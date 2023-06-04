package io.github.ilyadreamix.httptools.request.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPayload(
    @SerialName("type")
    val type: String,

    @SerialName("content")
    val content: String
)
