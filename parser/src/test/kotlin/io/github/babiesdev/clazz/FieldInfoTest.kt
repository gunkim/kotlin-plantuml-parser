package io.github.babiesdev.clazz

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs

class FieldInfoTest : BehaviorSpec({
    Given("두개를 변환") {
        val rawNoAccessor = "val name: String"
        val rawWithAccessor = "private val name: String"
        val expectFalse = "private fun name(): String"

        When("접근자가 없는 경우") {
            val result = FieldInfo.isField(rawNoAccessor)

            Then("접근자가 없다") {
                result.accessor shouldBe FieldInfo.Accessor.NONE
            }
        }

        When("접근자가 있는 경우") {
            val result = FieldInfo.isField(rawWithAccessor)

            Then("접근자가 있다") {
                result.accessor shouldBe FieldInfo.Accessor.PRIVATE
            }
        }

        When("잘못된 값") {
            val result = FieldInfo.isField(expectFalse)

            Then("False를 응답") {
                result shouldBeSameInstanceAs IllegalArgumentException("The value is not a field")
            }
        }
    }
})