package com.ll

import java.util.Scanner

class App {
    fun run() {
        println("== 명언 앱 ==")

        val scanner = Scanner(System.`in`)
        while (true) {
            print("명령) ")
            val cmd = scanner.nextLine().trim()

            if (cmd == "종료") {
                break
            }
        }
    }
}
