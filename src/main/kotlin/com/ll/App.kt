package com.ll

import java.io.File
import java.util.Scanner

class App {
    private val wiseSayings = mutableListOf<WiseSaying>()
    private var lastId = 0

    private val dataDir = File("db/wiseSaying")
    private val lastIdFile = File(dataDir, "lastId.txt")

    init {
        if (!dataDir.exists()) {
            dataDir.mkdirs()
        }

        if (lastIdFile.exists()) {
            lastId = lastIdFile.readText().trim().toIntOrNull() ?: 0
        }

        dataDir.listFiles { it.extension == "json" }?.forEach { file ->
            val ws = wiseSayingFromJson(file.readText())
            wiseSayings.add(ws)
        }
    }

    fun run() {
        val sc = Scanner(System.`in`)
        println("== 명언 앱 ==")

        while (true) {
            print("명령) ")
            val input = readlnOrNull()!!.trim()
            val rq = Rq(input)

            when (rq.action) {
                "종료" -> return

                "등록" -> {
                    print("명언 : ")
                    val quote = sc.nextLine().trim()

                    print("작가 : ")
                    val author = sc.nextLine().trim()

                    val id = ++lastId
                    val wiseSaying = WiseSaying(id, quote, author)
                    wiseSayings.add(wiseSaying)

                    // 저장
                    File(dataDir, "$id.json").writeText(wiseSaying.toJson())
                    lastIdFile.writeText("$lastId")

                    println("${id}번 명언이 등록되었습니다.")
                }

                "목록" -> {
                    println("번호 / 작가 / 명언")
                    println("----------------------")
                    wiseSayings
                        .sortedByDescending { it.id }
                        .forEach { println("${it.id} / ${it.author} / ${it.quote}") }
                }

                "삭제" -> {
                    val id = rq.getParamValueAsInt("id", 0)
                    if (id == 0) {
                        println("id를 정확히 입력해주세요.")
                        continue
                    }

                    val removed = wiseSayings.removeIf { it.id == id }
                    if (removed) {
                        // 삭제된 경우 해당 파일도 삭제
                        File(dataDir, "$id.json").delete()
                        println("${id}번 명언이 삭제되었습니다.")
                    } else {
                        println("${id}번 명언은 존재하지 않습니다.")
                    }
                }

                "수정" -> {
                    val id = rq.getParamValueAsInt("id", 0)
                    if (id == 0) {
                        println("id를 정확히 입력해주세요.")
                        continue
                    }

                    val index = wiseSayings.indexOfFirst { it.id == id }
                    if (index == -1) {
                        println("${id}번 명언은 존재하지 않습니다.")
                        continue
                    }

                    val current = wiseSayings[index]

                    println("명언(기존) : ${current.quote}")
                    print("명언 : ")
                    val newQuote = sc.nextLine().trim()

                    println("작가(기존) : ${current.author}")
                    print("작가 : ")
                    val newAuthor = sc.nextLine().trim()

                    wiseSayings[index] = current.copy(
                        quote = newQuote,
                        author = newAuthor
                    )

                    // 수정된 내용 저장
                    File(dataDir, "$id.json").writeText(wiseSayings[index].toJson())
                }
            }
        }
    }
}
