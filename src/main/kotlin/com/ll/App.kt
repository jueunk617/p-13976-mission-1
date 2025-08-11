package com.ll

import java.util.Scanner

class App {
    fun run() {
        val scanner = Scanner(System.`in`)
        println("== 명언 앱 ==")

        val wiseSayings = mutableListOf<WiseSaying>()
        var lastId = 0

        while (true) {
            print("명령) ")
            val cmd = scanner.nextLine().trim()

            when {
                cmd == "종료" -> break

                cmd == "등록" -> {
                    print("명언 : ")
                    val quote = scanner.nextLine().trim()

                    print("작가 : ")
                    val author = scanner.nextLine().trim()

                    val id = ++lastId
                    wiseSayings.add(WiseSaying(id, quote, author))

                    println("${id}번 명언이 등록되었습니다.")
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