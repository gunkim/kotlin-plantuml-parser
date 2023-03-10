package io.github.babiesdev.application.ast

data class ParsingClass(
    val type: String,
    val name: String,
    val fields: List<ParsingParameter>,
    val methods: List<ParsingFunction>,
    val visibility: String,
)
