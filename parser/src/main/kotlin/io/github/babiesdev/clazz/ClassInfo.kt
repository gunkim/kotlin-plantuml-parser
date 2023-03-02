package io.github.babiesdev.clazz

import java.nio.file.Path
import kotlin.io.path.extension

class ClassInfo(
    val name: String,
    val path: Path,
    val packages:String,
    val methods: List<MethodInfo>,
    val fields: List<FieldInfo>,
    val imports: List<ImportField>
) {
    init {
        require(path.extension == "kt"){ "The file extension must be .kt"}
    }
    companion object{
        fun fromPath(path: Path): ClassInfo {
            val name = path.fileName.toString().removeSuffix(".kt")
            val packages = path.parent.toString().replace("\\", ".").removePrefix("src.main.kotlin.")
            val methods = TODO()
            val fields = TODO()
            val imports = TODO()
            return ClassInfo(name, path, packages, methods, fields, imports)
        }
    }
}