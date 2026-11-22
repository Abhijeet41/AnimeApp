 ## AnimeApp

A Features with Layerwise multi‑module Jetpack Compose Android application following Clean Architecture and Single Source of Truth principle.
Features Room Database + Paging 3 for offline‑first data loading and Hilt for dependency injection.

```
AnimeApp/
│
├── app/                      # Main application module (navigation, DI entry point)
│   ├── src/main/java/com/abhi41/animeapp/
│   │   ├── BaseApplication.kt
│   │   ├── MainActivity.kt
│   │   ├── navigation/       # Navigation graphs (AnimeNavGraph, PlanetNavGraph, BaseNavGraph)
│   │   └── ui/theme/         # Material 3 theme, Color, Type
│
├── core-network/             # Shared network module (Retrofit, OkHttp, DTOs, ApiService)
│   ├── src/main/java/com/abhi41/planet/core_network/
│   │   ├── di/               # NetworkModule (Retrofit instance, OkHttpClient)
│   │   ├── dtos/             # API Data Transfer Objects (Characters, Details, Planets)
│   │   └── service/          # ApiService interface
│
├── anime/                    # Feature module: Anime
│   ├── data/                 # Data layer (Repository impl, mappers, DI)
│   │   ├── src/main/java/com/abhi41/anime/data/
│   │   │   ├── di/           # AnimeDataModule
│   │   │   ├── mappers/      # Data ↔ Domain mappers
│   │   │   └── repository/   # AnimeRepositoryImpl
│   │   │
│   ├── domain/               # Domain layer (Business logic, models, use cases, paging)
│   │   ├── src/main/java/com/abhi41/anime/domain/
│   │   │   ├── di/
│   │   │   ├── models/       # Character, CharacterDetails, CharactersResp, etc.
│   │   │   ├── paging/       # AnimeDataSource
│   │   │   ├── repository/   # AnimeRepository interface
│   │   │   └── useCases/     # GetAllCharactersUseCase, GetCharactersDetailsUseCase
│   │   │
│   └── presentation/         # Presentation layer (Jetpack Compose screens & viewmodels)
│       ├── src/main/java/com/abhi41/anime/presentation/
│       │   ├── character_details/ # CharacterDetailScreen, CharacterDetailViewModel
│       │   ├── characters/        # CharactersListScreen, CharactersViewModel
│       │   └── components/        # Reusable UI components (CharacterItem)
│
└── planet/                   # Feature module: Planet (Clean Architecture)
    ├── data/                 # Data layer
    │   ├── src/main/java/com/abhi41/planet/data/
    │   │   ├── di/           # PlanetModule
    │   │   ├── mappers/      # Mappers
    │   │   └── repository/   # PlanetRepositoryImpl
    │   │
    ├── domain/               # Domain layer
    │   ├── src/main/java/com/abhi41/planet/domain/
    │   │   ├── model/        # Character, PlanetInfoWithCharacters
    │   │   ├── repository/   # PlanetRepository interface
    │   │   └── useCases/     # GetPlanetInfoCharactersUseCase
    │   │
    └── presentation/         # Presentation layer
        └── src/main/java/com/abhi41/planet/presentation/
            └── planetInfo/   # PlanetInfoCharacterScreen, PlanetInfoViewModel

```

### 🛠 Tech Stack

UI: Jetpack Compose

Architecture: Multi‑module + Clean Architecture + Single Source of Truth

Database: Room (with Paging 3 & RemoteMediator)

Network: Retrofit + OkHttp (core-network module)

DI: Hilt

Async: Kotlin Coroutines + Flow

### ✨ Features

Offline‑first anime listing using Room + Paging 3.

Modularized feature‑based architecture for scalability.

Clean separation between data, domain, and presentation layers.

Hilt‑powered dependency injection across modules.


