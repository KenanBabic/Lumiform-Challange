package com.noke.lumiformchallange.data

import com.noke.lumiformchallange.data.model.ItemApiModel
import com.noke.lumiformchallange.data.model.ItemImageQuestionApiModel
import com.noke.lumiformchallange.data.model.ItemPageApiModel
import com.noke.lumiformchallange.data.model.ItemSectionApiModel
import com.noke.lumiformchallange.data.model.ItemTextQuestionApiModel
import com.noke.lumiformchallange.domain.model.ImageQuestion
import com.noke.lumiformchallange.domain.model.Item
import com.noke.lumiformchallange.domain.model.Page
import com.noke.lumiformchallange.domain.model.Section
import com.noke.lumiformchallange.domain.model.TextQuestion

fun ItemApiModel.toDomain(): Item {
    return when (this) {
        is ItemPageApiModel -> Page(
            type = type,
            title = title,
            items = items?.map { it.toDomain() }
        )

        is ItemSectionApiModel -> Section(
            type = type,
            title = title,
            items = items?.map { it.toDomain() }
        )

        is ItemTextQuestionApiModel -> TextQuestion(
            type = type,
            title = title
        )

        is ItemImageQuestionApiModel -> ImageQuestion(
            type = type,
            title = title,
            src = src
        )
    }
}

fun Item.toApiModel(): ItemApiModel {
    return when (this) {
        is Page -> ItemPageApiModel(
            type = type,
            title = title,
            items = items?.map { it.toApiModel() } ?: emptyList()
        )

        is Section -> ItemSectionApiModel(
            type = type,
            title = title,
            items = items?.map { it.toApiModel() } ?: emptyList()
        )

        is TextQuestion -> ItemTextQuestionApiModel(
            type = type,
            title = title
        )

        is ImageQuestion -> ItemImageQuestionApiModel(
            type = type,
            title = title,
            src = src
        )
    }
}

fun Item.flatten(): List<Item> {
    val result = mutableListOf<Item>()

    when (this) {
        is Page -> {
            result.add(this)
            items?.forEach { item ->
                result.addAll(item.flatten())
            }
        }

        is Section -> {
            result.add(this)
            items?.forEach { item ->
                result.addAll(item.flatten())
            }
        }

        is TextQuestion, is ImageQuestion -> {
            result.add(this)
        }
    }

    return result
}
