package io.github.babiesdev.domain.plantuml

class PumlMethod(
    private val name: String,
    private val returnType: String,
    private val parameters: List<PumlField>,
)
