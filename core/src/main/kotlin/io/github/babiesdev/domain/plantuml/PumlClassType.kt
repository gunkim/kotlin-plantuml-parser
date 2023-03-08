package io.github.babiesdev.domain.plantuml

enum class PumlClassType(
    val symbol: String
) {
    CLASS("class"),
    INTERFACE("interface"),
    ENUM("enum")
}
