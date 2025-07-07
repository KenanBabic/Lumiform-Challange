package com.noke.lumiformchallange.data.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class ApiModelDeserializer : JsonDeserializer<ItemApiModel> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ItemApiModel {
        val jsonObject = json.asJsonObject
        return when (val type = jsonObject.get("type").asString) {
            "page" -> {
                val title = jsonObject.get("title").asString
                val items = jsonObject.get("items")?.asJsonArray?.map {
                    context.deserialize<ItemApiModel>(it, ItemApiModel::class.java)
                } ?: emptyList()
                ItemPageApiModel(type, title, items)
            }

            "section" -> {
                val title = jsonObject.get("title").asString
                val items = jsonObject.get("items")?.asJsonArray?.map {
                    context.deserialize<ItemApiModel>(it, ItemApiModel::class.java)
                } ?: emptyList()
                ItemSectionApiModel(type, title, items)
            }

            "text" -> {
                val title = jsonObject.get("title").asString
                ItemTextQuestionApiModel(type, title)
            }

            "image" -> {
                val title = jsonObject.get("title").asString
                val src = jsonObject.get("src").asString
                ItemImageQuestionApiModel(type, title, src)
            }

            else -> throw JsonParseException("Unknown type: $type")
        }
    }
}