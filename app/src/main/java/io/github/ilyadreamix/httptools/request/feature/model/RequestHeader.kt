package io.github.ilyadreamix.httptools.request.feature.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestHeader(
    @SerialName("name")
    val name: String,

    @SerialName("value")
    val value: String
)
