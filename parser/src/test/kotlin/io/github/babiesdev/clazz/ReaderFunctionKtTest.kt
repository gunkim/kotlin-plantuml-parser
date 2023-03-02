package io.github.babiesdev.clazz

import org.junit.jupiter.api.Test
import java.nio.file.Paths

class ReaderFunctionKtTest {

    @Test
    fun readTest() {
        val clazz =
            pathToStrings(Paths.get("C:\\workspace\\kotlin-plantuml-parser\\core\\src\\main\\kotlin\\io\\github\\babiesdev\\sample\\TestData.kt"))
    }
}