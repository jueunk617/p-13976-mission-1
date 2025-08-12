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

fun wiseSayingFromJson(json: String): WiseSaying {
    val lines = json.trim().lines().map { it.trim() }

    val id = lines[1].removePrefix("\"id\": ").removeSuffix(",").toInt()
    val quote = lines[2].removePrefix("\"quote\": \"").removeSuffix("\",")
    val author = lines[3].removePrefix("\"author\": \"").removeSuffix("\"")

    return WiseSaying(id, quote, author)
}