package io.github.babiesdev.application

import io.github.babiesdev.application.ast.ParsingClass
import io.github.babiesdev.application.ast.ParsingFunction
import io.github.babiesdev.application.ast.ParsingParameter
import io.github.babiesdev.domain.PumlGenerator
import io.github.babiesdev.domain.plantuml.PumlClass
import io.github.babiesdev.domain.plantuml.PumlClassType
import io.github.babiesdev.domain.plantuml.PumlPackage
import kotlinx.ast.common.AstSource
import kotlinx.ast.common.ast.Ast
import kotlinx.ast.common.ast.AstNode
import kotlinx.ast.common.klass.KlassDeclaration
import kotlinx.ast.common.klass.KlassIdentifier
import kotlinx.ast.grammar.kotlin.common.summary
import kotlinx.ast.grammar.kotlin.common.summary.PackageHeader
import kotlinx.ast.grammar.kotlin.target.antlr.kotlin.KotlinGrammarAntlrKotlinParser
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.pathString

fun main() {
    val rootPath = Path.of("/Users/gunkim/private-workspace/kotlin-plantuml-parser/core/src/main/kotlin/io/github/babiesdev/domain/plantuml")

    val subPaths = rootPath.subdirectoriesPaths
    val asts = subPaths.flatMap(::convertPathToAsts)

    val packageName = asts.packageName
    val classes = pumlClasses(packageName, asts)

    generatePumlFile("./test.puml", classes)
}

private fun pumlClasses(
    packageName: String,
    asts: List<Ast>
) = asts
    .filterIsInstance<KlassDeclaration>()
    .map(::createParsingClass)
    .map { createPumlClass(it, packageName) }

private fun generatePumlFile(fileName: String, classes: List<PumlClass>) {
    val generator = PumlGenerator(classes)
    Path.of(fileName).toFile().writeText(generator.generate())
}

private fun createPumlClass(
    it: ParsingClass,
    header: String
) = PumlClass(
    name = it.name,
    type = PumlClassType.valueOf(it.type.uppercase()),
    basePackage = PumlPackage(header),
    fields = it.fields.map(ParsingParameter::convert),
    methods = it.methods.map(ParsingFunction::convert),
)

private fun createParsingClass(it: KlassDeclaration) = ParsingClass(
    type = if (it._modifiers == "enum") "enum" else it.keyword,
    name = it.identifier!!.rawName,
    fields = it._fields,
    methods = it._methods,
    visibility = it._modifiers
)

private fun convertPathToAsts(path: Path): List<Ast> =
    KotlinGrammarAntlrKotlinParser.parseKotlinFile(AstSource.File(path.pathString))
        .summary(true).get()

private val List<Ast>.packageName: String
    get() = first()
        .let { it as PackageHeader }
        .identifier.joinToString(
            separator = ".",
            transform = KlassIdentifier::rawName
        )

private val KlassDeclaration._modifiers: String
    get() = if (modifiers.isNotEmpty()) {
        modifiers.first().modifier
    } else {
        "public"
    }

private val KlassDeclaration._fields: List<ParsingParameter>
    get() {
        return if (parameter.isNotEmpty()) {
            parameter.first().parameter.map { param ->
                ParsingParameter(
                    param.identifier!!.rawName,
                    param.type.first().rawName,
                    if (param.modifiers.isEmpty()) {
                        "public"
                    } else {
                        param.modifiers.first().modifier
                    }
                )
            }
        } else {
            listOf()
        }
    }

private val KlassDeclaration._methods: List<ParsingFunction>
    get() {
        val astNode = children.last() as AstNode
        return astNode.children.filterIsInstance<KlassDeclaration>().map { it ->
            ParsingFunction(
                it.identifier!!.rawName,
                it.parameter.map(io.github.babiesdev.application.ast.ParsingParameter.Companion::convert),
                if (it.type.isEmpty()) {
                    ""
                } else {
                    it.type.first().rawName
                },
                if (it.modifiers.isEmpty()) {
                    "public"
                } else {
                    it.modifiers.first().modifier
                }
            )
        }
    }

private val Path.subdirectoriesPaths: List<Path>
    get() = if (isDirectory()) {
        listDirectoryEntries()
            .flatMap {
                if (it.isDirectory()) it.listDirectoryEntries()
                else listOf(it)
            }
    } else {
        listOf(this)
    }
