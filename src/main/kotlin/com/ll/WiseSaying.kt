package com.ll

data class WiseSaying(
    val id: Int,
    val quote: String,
    val author: String
)

fun WiseSaying.toJson(): String {
    return """
        {
          "id": $id,
          "quote": "$quote",
          "author": "$author"
        }
    """.trimIndent()
}

fun wiseSayingListFromJson(json: String): List<WiseSaying> {
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
