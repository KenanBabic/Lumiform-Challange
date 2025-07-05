package com.noke.lumiformchallange.data

import com.noke.lumiformchallange.data.model.ApiImageQuestion
import com.noke.lumiformchallange.data.model.ApiModel
import com.noke.lumiformchallange.data.model.ApiPage
import com.noke.lumiformchallange.data.model.ApiSection
import com.noke.lumiformchallange.data.model.ApiTextQuestion
import com.noke.lumiformchallange.domain.model.ImageQuestion
import com.noke.lumiformchallange.domain.model.Item
import com.noke.lumiformchallange.domain.model.Page
import com.noke.lumiformchallange.domain.model.Section
import com.noke.lumiformchallange.domain.model.TextQuestion

fun ApiModel.toDomain(): Item {
    return when (this) {
        is ApiPage -> Page(
            type = type,
            title = title,
            items = items?.map { it.toDomain() }
        )

        is ApiSection -> Section(
            type = type,
            title = title,
            items = items?.map { it.toDomain() }
        )

        is ApiTextQuestion -> TextQuestion(
            type = type,
            title = title
        )

        is ApiImageQuestion -> ImageQuestion(
            type = type,
            title = title,
            src = src
        )
    }
}
