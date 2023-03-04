package io.github.babiesdev.clazz

import java.nio.file.Path
import kotlin.io.path.extension

class ClassInfo(
    val className: String,
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
            val parsed = pathToStrings(path).lines()
            val className = path.fileName.toString().removeSuffix(".kt")
            val path = path
            // parsed의 첫번째 줄을 가져온뒤 첫번째을 삭제 한다
            val packages = parsed.first().removePrefix("package ")



            val methods = TODO()
            val fields = TODO()
            val imports = TODO()
            return ClassInfo(className, path, packages, methods, fields, imports)
        }
    }
}