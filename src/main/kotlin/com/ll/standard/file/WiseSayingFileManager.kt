package com.ll.standard.file

import com.ll.domain.wiseSaying.entity.WiseSaying
import com.ll.standard.util.json.JsonUtil
import java.io.File

class WiseSayingFileManager {
    private val dataDir = File("db/wiseSaying")
    private val lastIdFile = File(dataDir, "lastId.txt")
    private val dataJsonFile = File(dataDir, "data.json")

    init {
        if (!dataDir.exists()) {
            dataDir.mkdirs()
        }
    }

    fun loadAll(): Pair<List<WiseSaying>, Int> {
        val wiseSayings = if (dataJsonFile.exists()) {
            JsonUtil.fromJsonList(dataJsonFile.readText())
        } else {
            emptyList()
        }

        val lastId = if (lastIdFile.exists()) {
            lastIdFile.readText().trim().toIntOrNull() ?: 0
        } else 0

        return wiseSayings to lastId
    }

    fun saveLastId(lastId: Int) {
        lastIdFile.writeText("$lastId")
    }

    fun saveOne(wiseSaying: WiseSaying) {
        File(dataDir, "${wiseSaying.id}.json").writeText(JsonUtil.toJson(wiseSaying))
    }

    fun deleteOne(id: Int) {
        File(dataDir, "$id.json").delete()
    }

    fun saveAll(wiseSayings: List<WiseSaying>) {
        val jsonList = wiseSayings
            .sortedBy { it.id }
            .joinToString(separator = ",\n") { JsonUtil.toJson(it) }

        val finalJson = "[\n$jsonList\n]"
        dataJsonFile.writeText(finalJson)
    }

    fun clearAll() {
        dataDir.listFiles { it.extension == "json" }?.forEach { it.delete() }
        lastIdFile.delete()
        dataJsonFile.delete()
    }
}