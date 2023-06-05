package io.github.ilyadreamix.httptools.request.feature.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestExtension(
    @SerialName("name")
    val name: String,

    @SerialName("value")
    var value: String
)
