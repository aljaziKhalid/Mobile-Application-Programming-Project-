# Jetpack Libraries

This document lists the Android and Jetpack libraries planned for the Mystiq digital wardrobe application, their intended uses, and the reasons for selecting them.

| Library | Used for | Why we chose it |
|---|---|---|
| Jetpack Compose | Building all application screens and UI components. | It allows us to build the interface using Kotlin and update the UI when application state changes. |
| Material 3 | Creating cards, buttons, dialogs, snackbars, search fields, and filter chips. | It provides consistent UI components for displaying clothing items, selecting outfits, and showing confirmation messages. |
| Navigation Compose | Navigating between Home, Closet, Camera, Tagging, Spread, Vault, Archive, Sign-in, and Profile screens. | It organizes navigation between screens and supports passing data between destinations. |
| Lifecycle ViewModel | Managing screen state and handling user actions. | It separates UI logic from the interface and helps preserve screen state during configuration changes. |
| Kotlin Coroutines and Flow | Performing asynchronous operations and observing database changes. | They prevent long-running data operations from blocking the UI and allow screens to react to updated data. |
| Room | Storing clothing items, categories, outfits, drafts, tags, and wear history locally. | It supports structured data and relationships between clothing items and outfits while enabling offline access. |
| CameraX | Capturing clothing photos and displaying a live camera preview. | It provides lifecycle-aware camera functionality for adding clothing items to the digital closet. |
| AndroidX Activity | Hosting the Compose interface and handling activity-based results. | It integrates Compose with Android activity APIs and supports image selection and permission requests. |
| DataStore | Saving lightweight application preferences. | It provides persistent key-value storage for simple settings without requiring database tables. |
| WorkManager | Scheduling background tasks, such as periodic wear-tracking reminders. | It supports reliable scheduling of background work when the application is not actively open. |
| AndroidX Core | Providing Android compatibility utilities and notification-related support. | It simplifies access to Android APIs and provides compatibility utilities across Android versions. |
| AndroidX Palette | Extracting dominant colors from clothing images. | It supports the closet statistics feature by identifying prominent colors in clothing photos. |
| Hilt | Providing dependencies to ViewModels and other application components. | It reduces manual dependency creation and helps organize database and repository dependencies. |
| Coil | Loading and displaying clothing images in the Compose UI. | It simplifies loading images from URIs or file paths and displaying them efficiently in the application. |
| Lifecycle Runtime Compose | Collecting Flow-based ui state in Compose using `collectAsStateWithLifecycle()`. | It allows the UI to observe state in a lifecycle-aware way and avoids unnecessary collection when the screen is inactive. |
