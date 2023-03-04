package io.github.babiesdev.domain

import io.github.babiesdev.domain.plantuml.PumlClass

class PumlGenerator(
    private val packages: List<PumlClass>,
) {
    fun generate() = """
        |@startuml
        |
        |${packages.joinToString(separator = "") { it.toPlantUml() }}
        |
        |@enduml
    """.trimMargin()
}
