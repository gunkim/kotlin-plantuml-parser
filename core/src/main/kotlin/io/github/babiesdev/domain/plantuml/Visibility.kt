package io.github.babiesdev.domain.plantuml

enum class Visibility(val symbol: String) {
    PUBLIC("+"),
    PROTECTED("#"),
    PRIVATE("-"),
    NONE(""),
}
