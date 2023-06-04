package io.github.ilyadreamix.httptools.request.enumeration

enum class RequestAuthType(val filterText: String, val visibleText: String) {
    UNKNOWN("auth", "Auth"),
    BASIC("basic", "Basic"),
    DIGEST("digest", "Digest")
}
