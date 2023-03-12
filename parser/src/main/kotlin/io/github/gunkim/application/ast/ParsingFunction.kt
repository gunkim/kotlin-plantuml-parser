package io.github.gunkim.application.ast

import io.github.gunkim.domain.plantuml.PumlField
import io.github.gunkim.domain.plantuml.PumlMethod
import io.github.gunkim.domain.plantuml.Visibility

data class ParsingFunction(
    val name: String,
    val parsingParameters: List<ParsingParameter>,
    val returnType: String,
    val visibility: String,
) {
    fun convert() = PumlMethod(
        name = name,
        returnType = returnType,
        parameters = parsingParameters.map { param ->
            PumlField(
                name = param.name,
                type = param.type,
                visibility = Visibility.NONE,
            )
        }
    )
}
