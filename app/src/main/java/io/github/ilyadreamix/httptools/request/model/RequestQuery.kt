package io.github.ilyadreamix.httptools.request.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestQuery(
    @SerialName("name")
    val name: String,

    @SerialName("value")
    val value: String
)
