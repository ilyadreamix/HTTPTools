package io.github.ilyadreamix.httptools.request.model

import io.github.ilyadreamix.httptools.request.enumeration.RequestAuthType
import io.github.ilyadreamix.httptools.request.enumeration.RequestContentType
import io.github.ilyadreamix.httptools.request.enumeration.RequestMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Request(
    @SerialName("name")
    val name: String,

    @SerialName("url")
    val url: String,

    @SerialName("method")
    val method: RequestMethod,

    @SerialName("payload")
    val payload: RequestPayload? = null,

    @SerialName("headers")
    val headers: List<RequestHeader> = listOf(),

    @SerialName("queries")
    val queries: List<RequestQuery> = listOf(),

    @SerialName("createdAt")
    val createdAt: Long = System.currentTimeMillis(),

    @SerialName("id")
    val id: String = UUID.randomUUID().toString(),

    @SerialName("extensions")
    var extensions: List<RequestExtension> = listOf()
) {
    val favouriteTime: Long? get() {
        val favouriteExtension = extensions.find { it.name == "favourite" }
            ?: return null
        return favouriteExtension.value.toLong()
    }

    private val contentType: RequestContentType? get() {
        val contentTypeHeader = headers.find { it.name == "content-type" }
            ?: return null
        return RequestContentType.values().find { contentType ->
            contentType.filterText in contentTypeHeader.value
        }
    }

    private val authType: RequestAuthType? get() {
        val authTypeHeader = headers.find { it.name == "authorization" }
            ?: return null
        return RequestAuthType.values().find { authType ->
            authType.filterText in authTypeHeader.value
        } ?: RequestAuthType.UNKNOWN
    }

    fun uiChips(): List<String> {
        val requestChips = mutableListOf<String>()

        contentType?.let { contentType ->
            requestChips += contentType.visibleText
        }

        authType?.let { authType ->
            requestChips += authType.visibleText
        }

        if (url.startsWith("https://")) requestChips += "TLS"

        if (headers.any { it.name == "cookie" }) requestChips += "Cookie"

        return requestChips
    }
}
