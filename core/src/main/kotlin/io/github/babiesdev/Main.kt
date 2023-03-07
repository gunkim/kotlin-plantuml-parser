package io.github.babiesdev

import io.github.babiesdev.domain.PumlGenerator
import io.github.babiesdev.domain.plantuml.*
import kotlin.io.path.Path
import kotlin.io.path.writeText

fun main() {
    val pumlGenerator = PumlGenerator(
        listOf(
            PumlClass(
                "Main",
                PumlPackage("io.github.babiesdev"),
                listOf(
                    PumlField(
                        "args",
                        "Array<String>",
                    ),
                ),
                listOf(
                    PumlMethod(
                        name = "main",
                        parameters = listOf(
                            PumlField(
                                "args",
                                "Array<String>",
                            ),
                        ),
                    ),
                ),
                PumlRelation(
                    target = "io.github.babiesdev.Test",
                    type = RelationType.EXTENSION,
                )
            ),
        ),
    )

    val file = Path("./test.puml")
    file.writeText(pumlGenerator.generate())
}
