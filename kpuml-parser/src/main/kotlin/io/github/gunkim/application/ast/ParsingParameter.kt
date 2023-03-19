package io.github.gunkim.application.ast

import io.github.gunkim.domain.plantuml.PumlField
import io.github.gunkim.domain.plantuml.Visibility
import kotlinx.ast.common.klass.KlassDeclaration

data class ParsingParameter(
    val name: String,
    val type: String,
    val visibility: String,
) {
    fun convert() = PumlField(
        name = name,
        type = type,
        visibility = Visibility.valueOf(visibility.uppercase()),
    )

    companion object {
        fun convert(node: KlassDeclaration) = ParsingParameter(
            node.identifier!!.rawName,
            node.type.first().rawName,
            "NONE",
        )
    }
}
