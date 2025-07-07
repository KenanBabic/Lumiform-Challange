# Android Content Viewer

A modern Android application that displays hierarchical content from a REST API with offline
support, built using Kotlin and Jetpack Compose.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Building the App](#building-the-app)
## Introduction

Android Content Viewer is an Android application that fetches structured content from a remote REST
API and displays it in a clean, hierarchical format. It supports offline access via a local Room
database and offers robust error handling with real-time network monitoring.

## Features

- Fetch hierarchical content (Pages, Sections, Questions) from REST API
- Display content with proper visual hierarchy
- Async image loading with reduced preview and full-screen viewer
- Local caching with Room for offline support
- Real-time network monitoring with visual indicators
- Graceful error handling with retry capabilities
- Type-safe navigation with parameter serialization
- Compose Previews for UI development

## Architecture

The application follows **Clean Architecture principles** with a clear separation of concerns across
layers.

```
app/src/main/java/com/noke/lumiformchallange/
├── app/
│   ├── LumiformApplication.kt        # Application class for Hilt
│   └── MainActivity.kt                   # Single activity setup
├── data/
│   ├── local/
│   │   ├── dao/
│   │   │   └── ItemDao.kt               # Room DAO for local data access
│   │   ├── database/
│   │   │   └── AppDatabase.kt           # Room database configuration
│   │   ├── entity/
│   │   │   └── ItemEntity.kt            # Room entity definitions
│   │   ├── ItemLocalDataSource.kt       # Local data source interface
│   │   └── ItemLocalDataSourceImpl.kt   # Local data source implementation
│   ├── model/
│   │   ├── ApiModelDeserializer.kt      # Custom JSON deserializer
│   │   └── ItemApiModel.kt              # API response models
│   ├── remote/
│   │   ├── ItemApiService.kt            # Retrofit API interface
│   │   ├── ItemRemoteDataSource.kt      # Remote data source interface
│   │   ├── ItemRemoteDataSourceImpl.kt  # Remote data source implementation
│   │   ├── NetworkClient.kt             # Retrofit client configuration
│   │   ├── NetworkConnectivityChecker.kt # Network status monitoring
│   │   └── NetworkErrorHandler.kt       # Centralized error handling
│   ├── repository/
│   │   └── ItemRepositoryImpl.kt        # Repository implementation with caching
│   ├── ItemMapper.kt                    # Data to domain model mapping
│   └── Result.kt                        # Result wrapper for operations
├── di/
│   ├── DatabaseModule.kt                # Hilt database module
│   ├── ItemModule.kt                    # Hilt item-related dependencies
│   └── NetworkModule.kt                 # Hilt network module
├── domain/
│   ├── model/
│   │   └── Item.kt                      # Domain models (sealed classes)
│   └── ItemRepository.kt                # Repository interface
└── presentation/
    ├── components/
    │   └── ItemView.kt                  # Hierarchical content display
    ├── model/
    │   └── ImageUi.kt                   # UI models for navigation
    ├── navigation/
    │   ├── AppNavigation.kt             # Navigation graph setup
    │   ├── NavigationConstants.kt       # Custom NavType for type safety
    │   └── Screen.kt                    # Type-safe navigation screens
    ├── screen/
    │   ├── HomeScreen.kt                # Main content screen
    │   └── ImageDetailScreen.kt         # Full-screen image viewer
    ├── uistate/
    │   └── HomeUiState.kt               # UI state definitions
    └── viewmodel/
        └── HomeViewModel.kt             # ViewModel with state management
```

### Key architectural components:

- **Clean Architecture**: With distinct Domain, Data, and Presentation layers
- **MVVM Pattern**: ViewModels manage UI state and business logic
- **Repository Pattern**: Coordinates data from local and remote sources
- **Data Source Pattern**: Abstracts access to local (Room) and remote (API) sources
- **Hilt**: For dependency injection
- **StateFlow**: For reactive UI state management
- **Compose Navigation**: For type-safe screen navigation

## Getting Started

### Prerequisites

- Android Studio (latest stable version recommended)
- JDK 11 or higher
- Android SDK with minimum API level 26

### Building the App

1. **Clone the repository**
    ```bash
    git clone https://github.com/KenanBabic/Lumiform-Challange
    cd Lumiform-Challange
    ```

2. **Open in Android Studio**
    - Open the project folder
    - Wait for Gradle sync to complete

3. **Run the app**
    - Connect an Android device or start an emulator
    - Click "Run" in Android Studio or execute:
      ```bash
      ./gradlew assembleDebug
      ```

### UI

- **Jetpack Compose**: Modern toolkit for building native UI
- **Material 3**: For design system components
- **Compose Navigation**: For in-app navigation
- **Compose Previews**: For UI development and testing

### Data & Networking

- **Retrofit**: HTTP client for network requests
- **Gson**: For JSON serialization/deserialization
- **Room**: Local database with compile-time safety

### Dependency Injection

- **Hilt**: Dependency injection framework for Android

### Concurrency & State

- **Kotlin Coroutines**: For asynchronous programming
- **StateFlow / Flow**: For reactive state management

### Additional Utilities

- **kotlinx.serialization**: For type-safe parameter passing in navigation
