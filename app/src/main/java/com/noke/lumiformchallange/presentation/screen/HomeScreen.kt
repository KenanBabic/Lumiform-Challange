package com.noke.lumiformchallange.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.noke.lumiformchallange.domain.model.ImageQuestion
import com.noke.lumiformchallange.domain.model.Page
import com.noke.lumiformchallange.domain.model.Section
import com.noke.lumiformchallange.domain.model.TextQuestion
import com.noke.lumiformchallange.presentation.HomeUiState
import com.noke.lumiformchallange.presentation.ItemView
import com.noke.lumiformchallange.presentation.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onImageClick: (String, String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> LoadingContent(paddingValues)

                uiState.errorMessage != null -> Error(
                    errorMessage = uiState.errorMessage,
                    shouldShowRetry = uiState.shouldShowRetry,
                    onRetry = { viewModel.onRetryAction() }
                )

                else -> Content(
                    uiState = uiState,
                    onImageClick = onImageClick
                )
            }
        }
    }
}

@Composable
private fun Content(
    uiState: HomeUiState,
    onImageClick: (String, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(uiState.content) { item ->
            ItemView(
                item = item,
                onImageClick = onImageClick
            )
        }
    }
}

@Composable
private fun LoadingContent(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Error(
    errorMessage: String?,
    shouldShowRetry: Boolean,
    onRetry: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Connection Error",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onErrorContainer
            )

            Text(
                text = errorMessage ?: "Unknown error",
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            if (shouldShowRetry) {
                Button(
                    onClick = onRetry
                ) {
                    Text("Retry")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenErrorPreview() {
    MaterialTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Error(
                    errorMessage = "Failed to load content. Please check your internet connection.",
                    shouldShowRetry = true,
                    onRetry = { }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenSuccessPreview() {
    val mockContent = Page(
        type = "page",
        title = "Sample Form",
        items = listOf(
            Section(
                type = "section",
                title = "Personal Information",
                items = listOf(
                    TextQuestion(
                        type = "text",
                        title = "What is your name?"
                    ),
                    ImageQuestion(
                        type = "image",
                        title = "Upload your photo",
                        src = ""
                    )
                )
            ),
            TextQuestion(
                type = "text",
                title = "Additional comments"
            )
        )
    )

    MaterialTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
        }
    }
}