package com.noke.lumiformchallange.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    val cardColor = when (item) {
        is Page -> MaterialTheme.colorScheme.primaryContainer
        is Section -> MaterialTheme.colorScheme.secondaryContainer
        is TextQuestion -> MaterialTheme.colorScheme.surface
        is ImageQuestion -> MaterialTheme.colorScheme.surfaceVariant
    }

    val (horizontalPadding, cardPadding, elevation) = when (item) {
        is Page -> Triple(8.dp, 20.dp, 6.dp)
        is Section -> Triple(24.dp, 18.dp, 4.dp)
        is TextQuestion -> Triple(40.dp, 12.dp, 1.dp)
        is ImageQuestion -> Triple(40.dp, 12.dp, 1.dp)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = horizontalPadding,
                end = 12.dp,
                top = 6.dp,
                bottom = 6.dp
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(cardPadding)
        ) {
            when (item) {
                is Page -> {
                    Text(
                        text = item.title,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }

                is Section -> {
                    Text(
                        text = item.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }

                is TextQuestion -> {
                    Text(
                        text = item.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                is ImageQuestion -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = item.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        AsyncImage(
                            model = item.src,
                            contentDescription = item.title,
                            placeholder = painterResource(R.drawable.ic_launcher_foreground),
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    onImageClick(item.src, item.title)
                                },
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}
