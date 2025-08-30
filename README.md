# NewsApp - MVVM Clean Architecture with Dagger

A modern Android news application built with Clean Architecture, MVVM pattern, and Dagger 2 for dependency injection.

## 🏗️ Architecture

- **Clean Architecture** with separate modules: `app`, `data`, `domain`, `presentation`
- **MVVM Pattern** with ViewModels and StateFlow
- **Dagger 2** for dependency injection
- **Jetpack Compose** for modern UI
- **Paging 3** for efficient data loading
- **Retrofit** with Moshi for networking
- **Coroutines & Flow** for asynchronous operations

## 🚀 Features

- Browse top headlines by country
- Explore news sources
- Filter by languages and countries
- Search news articles
- Custom Chrome Tabs for article viewing
- Offline-first architecture with error handling

## 🔧 Build Instructions

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

### Run Tests
```bash
./gradlew test
```

## 📱 Play Store Ready

This app is configured for Play Store submission with:
- ✅ Secure API key management (BuildConfig)
- ✅ ProGuard configuration for release builds
- ✅ App signing configuration
- ✅ Privacy policy included
- ✅ Proper permissions and manifest setup

## 🔐 Security

- API keys are stored in BuildConfig (not hardcoded)
- Debug logging disabled in release builds
- ProGuard rules for code obfuscation
- Network security best practices

## 📄 Privacy

See [PRIVACY_POLICY.md](PRIVACY_POLICY.md) for the complete privacy policy.

## 🧪 Testing

- Unit tests for domain layer (use cases)
- Unit tests for data layer (repository, data sources)
- MockK for mocking dependencies
- Coroutines testing support

## 📦 Modules

- **app**: Main application module with DI setup
- **domain**: Business logic and entities
- **data**: Data sources and repository implementations
- **presentation**: UI components and ViewModels

## 🛠️ Tech Stack

- Kotlin
- Jetpack Compose
- Dagger 2
- Retrofit + Moshi
- OkHttp
- Paging 3
- Coroutines + Flow
- JUnit + MockK

---
*This is a learning project demonstrating modern Android development practices.*