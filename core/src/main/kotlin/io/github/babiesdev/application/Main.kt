package io.github.babiesdev.application

import io.github.babiesdev.domain.PumlGenerator
import io.github.babiesdev.domain.plantuml.*
import kotlinx.ast.common.AstSource
import kotlinx.ast.common.ast.Ast
import kotlinx.ast.common.ast.AstNode
import kotlinx.ast.common.klass.KlassDeclaration
import kotlinx.ast.grammar.kotlin.common.summary
import kotlinx.ast.grammar.kotlin.common.summary.PackageHeader
import kotlinx.ast.grammar.kotlin.target.antlr.kotlin.KotlinGrammarAntlrKotlinParser
import java.nio.file.Path
import java.util.*
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.pathString

data class Parameter(
    val name: String,
    val type: String,
    val visibility: String,
)

data class Function(
    val name: String,
    val parameters: List<Parameter>,
    val returnType: String,
    val visibility: String,
)

fun main() {
    val path =
        Path.of("/Users/gunkim/private-workspace/kotlin-plantuml-parser/core/src/main/kotlin/io/github/babiesdev/domain")

    val targets: List<Path> = path.listDirectoryEntries()
        .flatMap {
            if (it.isDirectory()) {
                it.listDirectoryEntries()
            } else {
                listOf(it)
            }
        }

    val astList: List<Ast> = targets
        .map { AstSource.File(it.pathString) }
        .map { KotlinGrammarAntlrKotlinParser.parseKotlinFile(it) }
        .flatMap { it.summary(true).get() }
    val classes = mutableListOf<PumlClass>()

    var header: String? = null
    astList.forEach { root ->
        if (root is PackageHeader) {
            header = root.identifier.joinToString(".") { it.rawName }
        }
        if (root is KlassDeclaration) {
            if (root.keyword == "class" || root.keyword == "interface") {
                val modifiers = if (root.modifiers.isNotEmpty()) {
                    root.modifiers.first().modifier
                } else {
                    "public"
                }
                val fields: List<Parameter> = if (root.parameter.isEmpty()) {
                    listOf()
                } else {
                    root.parameter.first().parameter.map { param ->
                        Parameter(
                            param.identifier!!.rawName,
                            param.type.first().rawName,
                            (if (param.modifiers.isEmpty()) {
                                "public"
                            } else {
                                param.modifiers.first().modifier
                            }).uppercase(Locale.getDefault())
                        )
                    }
                }

                val astNode = root.children.last() as AstNode
                val functions = astNode.children.filterIsInstance<KlassDeclaration>().map { child ->
                    Function(
                        child.identifier!!.rawName,
                        child.parameter.map { funa ->
                            Parameter(
                                funa.identifier!!.rawName,
                                funa.type.first().rawName,
                                "NONE"
                            )
                        },
                        if (child.type.isEmpty()) {
                            ""
                        } else {
                            child.type.first().rawName
                        },
                        (if (child.modifiers.isEmpty()) {
                            "public"
                        } else {
                            child.modifiers.first().modifier
                        }).uppercase(Locale.getDefault())
                    )
                }

                classes.add(
                    PumlClass(
                        name = root.identifier!!.rawName,
                        type = if (modifiers == "enum") {
                            PumlClassType.ENUM
                        } else {
                            PumlClassType.valueOf(root.keyword.uppercase(Locale.getDefault()))
                        },
                        basePackage = PumlPackage(header!!),
                        fields = fields.map { field ->
                            PumlField(
                                name = field.name,
                                type = field.type,
                                visibility = Visibility.valueOf(field.visibility),
                            )
                        },
                        methods = functions.map { func ->
                            PumlMethod(
                                name = func.name,
                                returnType = func.returnType,
                                parameters = func.parameters.map { param ->
                                    PumlField(
                                        name = param.name,
                                        type = param.type,
                                        visibility = Visibility.NONE,
                                    )
                                }
                            )
                        },
                    )
                )
            }
        }
    }

    val generator = PumlGenerator(classes)
    Path.of("./test.puml").toFile().writeText(generator.generate())
}
