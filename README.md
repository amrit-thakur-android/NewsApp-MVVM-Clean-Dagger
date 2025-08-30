# NewsApp - MVVM Clean Architecture with Dagger 2

A modern Android news application built with Clean Architecture, MVVM pattern, and **Dagger 2** for dependency injection.

> 🔗 **Also Available**: [NewsApp - Hilt Edition](https://github.com/amritthakur/NewsApp-MVVM-Clean-Hilt) *(Coming Soon)*
> Compare the same architecture implemented with **Hilt** dependency injection!

## 🏗️ Architecture

- **Clean Architecture** with separate modules: `app`, `data`, `domain`, `presentation`
- **MVVM Pattern** with ViewModels and StateFlow
- **Dagger 2** for dependency injection *(Manual DI setup with @Component, @Module)*
- **Jetpack Compose** for modern UI
- **Paging 3** for efficient data loading
- **Retrofit** with Moshi for networking
- **Coroutines & Flow** for asynchronous operations

## 🆚 **Dagger 2 vs Hilt Comparison**

This project uses **Dagger 2** which requires:
- Manual component setup (`@Component`, `@Module`)
- Explicit dependency graph management
- More boilerplate but full control
- Great for understanding DI fundamentals

*For a **Hilt** implementation with simplified setup, check out the companion project!*

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

### Release Build (APK)
```bash
./gradlew assembleRelease
```

### Release Build (AAB - Recommended for Play Store)
```bash
./gradlew bundleRelease
```

### Run Tests
```bash
./gradlew test
```

## 📱 Play Store Ready

This app is configured for Play Store submission with:
- ✅ Secure API key management (BuildConfig)
- ✅ ProGuard configuration for release builds
- ✅ Google Play App Signing ready (no local keystores needed)
- ✅ Android App Bundle (AAB) support
- ✅ Privacy policy included
- ✅ Proper permissions and manifest setup

## 🔐 Security

- API keys are stored in BuildConfig (not hardcoded)
- Debug logging disabled in release builds
- ProGuard rules for code obfuscation
- Network security best practices

## 📱 Local Testing

- **Debug builds**: Automatically signed, ready to install
- **Release builds**: Signed with debug keystore for local testing
- **Play Store**: Google Play App Signing handles production signing

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

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **DI**: Dagger 2 (Manual setup)
- **Networking**: Retrofit + Moshi + OkHttp
- **Async**: Coroutines + Flow
- **Pagination**: Paging 3
- **Testing**: JUnit + MockK
- **Architecture**: Clean Architecture + MVVM

## 📚 **Learning Series**

This is part of a learning series comparing different DI approaches:

1. **📍 Current**: [NewsApp with Dagger 2](.) - Manual DI setup
2. **🔜 Coming**: NewsApp with Hilt - Simplified DI with annotations

Both projects implement identical features using Clean Architecture, allowing you to compare the DI approaches side-by-side!

---
*This is a learning project demonstrating modern Android development practices with Dagger 2.*