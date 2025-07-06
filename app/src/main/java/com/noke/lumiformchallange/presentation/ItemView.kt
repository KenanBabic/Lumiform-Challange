package com.noke.lumiformchallange.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.noke.lumiformchallange.R
import com.noke.lumiformchallange.domain.model.ImageQuestion
import com.noke.lumiformchallange.domain.model.Item
import com.noke.lumiformchallange.domain.model.Page
import com.noke.lumiformchallange.domain.model.Section
import com.noke.lumiformchallange.domain.model.TextQuestion

@Composable
fun ItemView(
    item: Item,
    level: Int,
    onImageClick: (String, String) -> Unit
) {
    val fontSize = when (level) {
        0 -> 24.sp
        1 -> 20.sp
        2 -> 18.sp
        else -> 16.sp
    }

    val fontWeight = when (level) {
        0 -> FontWeight.Bold
        1 -> FontWeight.SemiBold
        else -> FontWeight.Medium
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = (level * 16).dp,
                top = 8.dp,
                end = 8.dp,
                bottom = 8.dp
            )
    ) {
        when (item) {
            is Page -> {
                Text(
                    text = item.title,
                    fontSize = fontSize,
                    fontWeight = fontWeight,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                item.items?.forEach { childItem ->
                    ItemView(
                        item = childItem,
                        level = level + 1,
                        onImageClick = onImageClick
                    )
                }
            }

            is Section -> {
                Text(
                    text = item.title,
                    fontSize = fontSize,
                    fontWeight = fontWeight,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                item.items?.forEach { childItem ->
                    ItemView (
                        item = childItem,
                        level = level + 1,
                        onImageClick = onImageClick
                    )
                }
            }

            is TextQuestion -> {
                Text(
                    text = item.title,
                    fontSize = fontSize,
                    fontWeight = fontWeight,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            is ImageQuestion -> {
                Text(
                    text = item.title,
                    fontSize = fontSize,
                    fontWeight = fontWeight,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                AsyncImage(
                    model = item.src,
                    contentDescription = item.title,
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            onImageClick(item.src, item.title)
                        },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}