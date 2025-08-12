package com.ll.global.bean

import com.ll.domain.wiseSaying.repository.WiseSayingFileRepository
import com.ll.domain.wiseSaying.repository.WiseSayingRepository

object SingletonScope {
    val wiseSayingRepository: WiseSayingRepository by lazy {
        WiseSayingFileRepository()
    }

    val wiseSayingFileRepository: WiseSayingFileRepository
        get() = wiseSayingRepository as WiseSayingFileRepository
}
