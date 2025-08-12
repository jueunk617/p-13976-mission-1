package com.ll.standard.util.json

import com.ll.domain.wiseSaying.entity.WiseSaying

object JsonUtil {
    fun toJson(wiseSaying: WiseSaying): String {
        return """
            {
              "id": ${wiseSaying.id},
              "quote": "${wiseSaying.quote}",
              "author": "${wiseSaying.author}"
            }
        """.trimIndent()
    }

    fun fromJsonList(json: String): List<WiseSaying> {
        val result = mutableListOf<WiseSaying>()

        val trimmed = json.trim()
            .removePrefix("[")
            .removeSuffix("]")
            .trim()

        val chunks = trimmed.split("},")
            .map { it.trim().let { s -> if (!s.endsWith("}")) "$s}" else s } }

        for (chunk in chunks) {
            val lines = chunk.lines().map { it.trim() }

            val id = lines[1].removePrefix("\"id\": ").removeSuffix(",").toInt()
            val quote = lines[2].removePrefix("\"quote\": \"").removeSuffix("\",")
            val author = lines[3].removePrefix("\"author\": \"").removeSuffix("\"")

            result.add(WiseSaying(id, quote, author))
        }

        return result
    }
}