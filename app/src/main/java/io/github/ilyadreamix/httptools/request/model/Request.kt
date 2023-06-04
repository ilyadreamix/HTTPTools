package io.github.ilyadreamix.httptools.request.model

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
    val extensions: List<String> = listOf()
) {
    val favouriteTime: Long? get() {
        val favouriteExtension = extensions.find { "favourite" in it }
            ?: return null
        return favouriteExtension.split("=")[1].toLong()
    }

    private val contentType: String? get() {
        val contentTypeHeader = headers.find { it.name == "content-type" }
            ?: return null
        val contentTypes = listOf("json", "xml", "javascript", "html", "image", "audio", "octet-stream")
        return contentTypes.find { it in contentTypeHeader.value }
    }

    fun chips(): List<String> {
        val requestChips = mutableListOf<String>()

        contentType?.let { contentType ->
            requestChips.add(contentType)
        }

        return requestChips
    }
}
