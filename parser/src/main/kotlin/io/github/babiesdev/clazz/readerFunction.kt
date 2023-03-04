package io.github.babiesdev.clazz

import java.io.FileInputStream
import java.nio.file.Path

fun pathToStrings(path:Path): String = FileInputStream(path.toFile()).use { stream ->
        ByteArray(stream.available())
            .also { stream.read(it) }
            .let { return String(it) }
    }

fun readPartsRange(raw:String){
    val imports = Regex("""import\s+.*;""")
}