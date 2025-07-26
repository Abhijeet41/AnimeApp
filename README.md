## AnimeApp

A multi‑module Jetpack Compose Android application following Clean Architecture and Single Source of Truth principle.
Features Room Database + Paging 3 for offline‑first data loading and Hilt for dependency injection.

```
AnimeApp/
│
├── app/                      # Main application module (navigation, DI entry point)
│
├── core-network/             # Shared network module (Retrofit, OkHttp, API setup)
│
├── anime/                    # Feature module: Anime
│   ├── data/                 # Data layer (Room, RemoteMediator, Repository)
│   │   ├── di/               # Hilt modules for DB, Repo, UseCases
│   │   ├── local/            # Room database (Entity, DAO, Database)
│   │   ├── mappers/          # Entity ↔ Domain mappers
│   │   ├── remote/           # Retrofit API + Paging RemoteMediator
│   │   └── repository/       # Repository implementation
│   │
│   ├── domain/               # Domain layer (Business logic)
│   │   ├── models/           # Domain models
│   │   ├── repository/       # Repository interfaces
│   │   └── useCases/         # Use cases (e.g., GetPagedAnimeUseCase)
│   │
│   └── presentation/         # Presentation layer (Jetpack Compose screens)
│       ├── characters/       # Anime list screen
│       ├── character_details/# Anime detail screen
│       ├── components/       # Reusable Compose components
│
├── planet/                   # Feature module: Planet (similar clean structure)
│   ├── data/
│   │   ├── di/
│   │   ├── mappers/
│   │   ├── remote/
│   │   └── repository/
│   │
│   ├── domain/
│   │   ├── model/
│   │   ├── repository/
│   │   └── useCases/
│   │
│   └── presentation/
│       └── planetInfo/       # Screens + ViewModels for planet feature

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


