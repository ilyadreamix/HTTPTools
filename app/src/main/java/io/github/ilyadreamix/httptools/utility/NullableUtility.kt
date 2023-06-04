package io.github.ilyadreamix.httptools.utility

infix fun <T> T.ifNull(value: T?) =
    takeIf { value == null }
