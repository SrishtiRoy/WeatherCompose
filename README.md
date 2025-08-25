# WeatherCompose

A clean and modern Android weather app built with **Kotlin** and **Jetpack Compose**. The project demonstrates a pragmatic **MVVM + Clean Architecture** setup with **Hilt** for DI and a simple **network/data** layer for fetching and rendering weather data.

---

## 🌟 Highlights (at a glance)
- **100% Jetpack Compose** UI with theming and dark‑mode support
- **MVVM + Clean Architecture**: clear separation of `ui`/`pages`, `data`, `network`, and `utils`
- **Hilt DI** modules for easy wiring and testability
- **Coroutines + Flow** for async and reactive state
- **Single-Activity** navigation pattern for simplicity
- **Composable custom UI** elements under `customuis/`
- **Extensible**: drop‑in API client and model mapping for any weather provider

---

## 🗂️ Project Structure
```
app/src/main/java/com/example/weatherappudemy/
├─ customuis/          # Reusable composables (cards, loaders, etc.)
├─ data/               # Models, repositories, mappers
├─ network/            # Retrofit/OkHttp services, interceptors
├─ pages/              # Screen-level composables (Home, Details, Settings)
├─ ui/theme/           # Color, Typography, Shapes, Theme
├─ utils/              # Helpers, result wrappers, extensions
├─ HiltModules.kt      # DI graph: Network, Repository, UseCases
├─ MainActivity.kt     # Single-activity host
└─ WeatherApplication.kt # Hilt-enabled Application
```

---

## 🧩 Tech Stack
- **Language:** Kotlin
- **UI:** Jetpack Compose, Material 3
- **Architecture:** MVVM + Clean Architecture layering
- **DI:** Hilt (Dagger)
- **Async:** Kotlin Coroutines & Flow
- **Networking:** Retrofit (OkHttp)
- **Image Loading:** Coil (if needed in UI cards)
- **Build:** Gradle Kotlin DSL (if enabled)

> Note: Swap/extend pieces as needed (e.g., add Room, Datastore, Ktor client).

---

## 📸 Screenshots
Below is an initial screenshot (repo structure). Add more app screens under `assets/screenshots/` and reference them here.

![Repo Structure](assets/<img width="1080" height="2400" alt="Screenshot_20250825_153722 copy" src="https://github.com/user-attachments/assets/9247d2d7-0531-4958-bba5-89d24349356b" />


---

## 🚀 Getting Started
- **Clone:** `git clone https://github.com/<you>/WeatherCompose.git`
- **Open in Android Studio** (Giraffe or newer recommended)
- **Sync Gradle** and run on an emulator/device (Android 8.0+)
- **API keys:** If your weather API needs a key, expose it via:
  - `local.properties` → `WEATHER_API_KEY=XXXX`
  - or a `BuildConfig` field injected via `gradle`

### Minimal DI Example (Hilt)
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
}
```

---

## 🧠 Key Concepts
- **State hoisting with ViewModels:** UI observes `StateFlow`/`MutableState` from VM
- **Unidirectional data flow:** Events → VM → state → Composables
- **Sealed UI states:** `Loading | Success(data) | Error(message)`
- **Composable reuse:** shared building blocks inside `customuis/`

---

## 🧪 Testing Ideas
- ViewModel unit tests with coroutine `TestDispatcher`
- Repository tests using fake/dummy data sources
- UI screenshot tests for stable components
- Network tests via MockWebServer

---

## 🗺️ Roadmap / TODO
- Add search + recent cities
- Add offline caching (Datastore/Room)
- Add location permissions + current location
- Add theming toggles and dynamic color
- Add instrumentation tests (Compose testing APIs)

---

## 📦 Packaging & Naming
- Base package: `com.example.weatherappudemy`
- Rename safely via **Refactor > Rename** if you plan to publish

---

## 🤝 Contributing
- Open issues/PRs for features, bugs, or docs
- Keep functions small, composables stateless where possible
- Prefer interfaces for repositories and DI‑backed bindings

---

## 📜 License
MIT — do what you want, but please keep the copyright and attribution.
