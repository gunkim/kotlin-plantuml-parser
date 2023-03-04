package io.github.babiesdev.domain.plantuml

class PumlClass(
    private val name: String,
    private val fields: List<PumlField>,
    private val methods: List<PumlMethod>,
)
