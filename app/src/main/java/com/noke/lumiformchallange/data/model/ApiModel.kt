package com.noke.lumiformchallange.data.model

sealed class ItemApiModel {
    abstract val type: String
    abstract val title: String
}

data class ItemPageApiModel(
    override val type: String,
    override val title: String,
    val items: List<ItemApiModel>?
) : ItemApiModel()

data class ItemSectionApiModel(
    override val type: String,
    override val title: String,
    val items: List<ItemApiModel>?
) : ItemApiModel()

data class ItemTextQuestionApiModel(
    override val type: String,
    override val title: String
) : ItemApiModel()

data class ItemImageQuestionApiModel(
    override val type: String,
    override val title: String,
    val src: String
) : ItemApiModel()