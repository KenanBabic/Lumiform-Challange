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
    onImageClick: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        when (item) {
            is Page -> {
                Text(
                    text = item.title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            is Section -> {
                Text(
                    text = item.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                )
            }

            is TextQuestion -> {
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 4.dp, start = 32.dp)
                )
            }

            is ImageQuestion -> {
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp, start = 32.dp)
                )

                AsyncImage(
                    model = item.src,
                    contentDescription = item.title,
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    modifier = Modifier
                        .padding(start = 32.dp)
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
