package io.github.ilyadreamix.httptools.request.model

import io.github.ilyadreamix.httptools.request.enumeration.RequestMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestList(
    @SerialName("requests")
    val requests: List<Request>
)

fun List<Request>?.sortedByFavouriteTime() = this?.sortedByDescending { it.favouriteTime } ?: listOf()

fun stubRequestList() = (0..500).map { itemIndex ->
    Request(
        name = "Example $itemIndex",
        url = "https://example$itemIndex.com/endpoint",
        method = RequestMethod.values().random(),
        headers = listOf(
            RequestHeader(
                name = "content-type",
                value = "application/json"
            )
        ),
        extensions =
            if ((0..500).random() <= 2) listOf("favourite=${System.currentTimeMillis()}")
            else listOf()
    )
}
