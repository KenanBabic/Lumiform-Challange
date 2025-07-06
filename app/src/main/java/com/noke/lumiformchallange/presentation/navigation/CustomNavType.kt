package com.noke.lumiformchallange.presentation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.noke.lumiformchallange.presentation.ImageUi
import kotlinx.serialization.json.Json

object CustomNavType {
    val ImageDetails = object : NavType<ImageUi>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): ImageUi? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): ImageUi {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: ImageUi) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: ImageUi): String {
            return Uri.encode(Json.encodeToString(value))
        }
    }
}

