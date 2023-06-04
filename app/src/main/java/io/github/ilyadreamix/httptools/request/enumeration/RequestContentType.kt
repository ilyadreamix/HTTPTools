package io.github.ilyadreamix.httptools.request.enumeration

enum class RequestContentType(val filterText: String, val visibleText: String) {
    JSON("json", "JSON"),
    XML("xml", "XML"),
    JAVA_SCRIPT("javascript", "JavaScript"),
    HTML("html", "HTML"),
    IMAGE("image", "Image"),
    Audio("audio", "Audio"),
    BINARY("octet-stream", "Binary")
}
