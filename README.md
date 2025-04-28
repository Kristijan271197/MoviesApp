Movie App - Android Technical Assignment

Overview


MovieApp is a Kotlin-based Android application that displays a list of movies from The Movie Database (TMDB) API. The app features four screens:

- Main screen with a search bar, movie/series filter and a scrolling list for the movies.

- Details screen showing all the movie information with an option to add the movie as a favorite.

- Favorites screen showing the titles of all the movies marked as favorites.

- Favorites details screen showing the details of the selected favorite movie.

The app follows modern Android development best practices and includes additional features like locally caching the favorite movies and their information for offline use.

Setup Instructions
Prerequisites
- Android Studio (latest stable version recommended)
- Java JDK 11 or higher

Installation
- Clone the repository: https://github.com/Kristijan271197/MoviesApp.git
- Open the project in Android Studio
- Build and run the app on an emulator or physical device

Architecture, Design Choices and Libraries:

Kotlin
- The project is written entirely in Kotlin, using features like coroutines, sealed classes, flows, etc.

Clean Architecture with MVVM
- The app follows Clean Architecture principles with MVVM (Model-View-ViewModel) pattern

Single-Activity Architecture with Jetpack Compose
- Jetpack Compose Navigation is used for screen transitions, providing type-safe navigation with a clean API.

State Management in Compose
The app uses Compose's state management system with:
- State and MutableState for UI state
- ViewModel to hold and manage UI-related data

Dependency Injection with Hilt
- Dagger Hilt is used for dependency injection

Retrofit
 - Retrofit is used for API calls

Coil
- For efficient image loading

Room
- For local storage

Testing
- JUnit: Core testing framework
- MockK: Mocking library for Kotlin
- Kotlin Test: Additional Kotlin test utilities
- Turbine: For testing Kotlin Flows

Error Handling
The app implements comprehensive error handling:
- Network errors
- API errors
- Database errors
- UI state errors (loading, success, error states)
- Sealed interfaces are used to represent different states
