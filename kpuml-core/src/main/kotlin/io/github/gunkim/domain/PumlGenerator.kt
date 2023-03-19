package io.github.gunkim.domain

import io.github.gunkim.domain.plantuml.PumlClass

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
