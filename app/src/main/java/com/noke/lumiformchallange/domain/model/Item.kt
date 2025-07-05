package com.noke.lumiformchallange.domain.model

sealed class Item {
    abstract val type: String
    abstract val title: String
}

data class Page(
    override val type: String,
    override val title: String,
    val items: List<Item>?
) : Item()

data class Section(
    override val type: String,
    override val title: String,
    val items: List<Item>?
) : Item()

data class TextQuestion(
    override val type: String,
    override val title: String
) : Item()

data class ImageQuestion(
    override val type: String,
    override val title: String,
    val src: String
) : Item()