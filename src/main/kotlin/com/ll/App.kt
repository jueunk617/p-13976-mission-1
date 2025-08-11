package com.ll

import java.util.Scanner

class App {
    private val wiseSayings = mutableListOf<WiseSaying>()
    private var lastId = 0

    fun run() {
        val sc = Scanner(System.`in`)
        println("== 명언 앱 ==")

        while (true) {
            print("명령) ")
            val cmd = sc.nextLine().trim()

            when (cmd) {
                "종료" -> return

                "등록" -> {
                    print("명언 : ")
                    val quote = sc.nextLine().trim()

                    print("작가 : ")
                    val author = sc.nextLine().trim()

                    val id = ++lastId
                    wiseSayings.add(WiseSaying(id, quote, author))

                    println("${id}번 명언이 등록되었습니다.")
                }

                "목록" -> {
                    println("번호 / 작가 / 명언")
                    println("----------------------")
                    wiseSayings
                        .sortedByDescending { it.id }
                        .forEach { println("${it.id} / ${it.author} / ${it.quote}") }
                }
            }
        }
    }
}

data class WiseSaying(
    val id: Int,
    val quote: String,
    val author: String
)