package com.ll

import java.util.Scanner

class App {
    fun run() {
        val scanner = Scanner(System.`in`)
        println("== 명언 앱 ==")

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
                }
            }
        }
    }
}
