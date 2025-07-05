package com.noke.lumiformchallange.data.model

sealed class ApiModel {
    abstract val type: String
    abstract val title: String
}

data class ApiPage(
    override val type: String,
    override val title: String,
    val items: List<ApiModel>?
) : ApiModel()

data class ApiSection(
    override val type: String,
    override val title: String,
    val items: List<ApiModel>?
) : ApiModel()

data class ApiTextQuestion(
    override val type: String,
    override val title: String
) : ApiModel()

data class ApiImageQuestion(
    override val type: String,
    override val title: String,
    val src: String
) : ApiModel()