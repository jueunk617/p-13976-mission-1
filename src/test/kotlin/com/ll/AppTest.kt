package com.ll

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AppTest {

    fun runApp(input: String): String {
        val originalIn = System.`in`
        val originalOut = System.out

        try {
            val inputStream = input.byteInputStream()
            System.setIn(inputStream)

            val outputStream = java.io.ByteArrayOutputStream()
            System.setOut(java.io.PrintStream(outputStream))

            App().run()

            return outputStream.toString()
        } finally {
            System.setIn(originalIn)
            System.setOut(originalOut)
        }
    }

    @Test
    @DisplayName("앱 시작")
    fun t1() {
        val input = "종료\n"
        val output = runApp(input)

        assertTrue(output.contains("== 명언 앱 =="))
    }

    @Test
    @DisplayName("등록 - 명언, 작가")
    fun t2() {
        val input = """
            등록
            현재를 사랑하라.
            작자미상
            종료
        """.trimIndent()

        val output = runApp(input)

        assertTrue(output.contains("명언 :"))
        assertTrue(output.contains("작가 :"))
    }

}