# Mystiq Application Architecture

Mystiq follows a three-layer architecture consisting of the **UI, Domain, and Data layers**. Hilt is used for dependency injection to connect the application components. Each layer has a clearly defined responsibility, and dependencies follow the architecture's boundaries. The UI layer does not access the database directly, and the Domain layer is independent of Android-specific frameworks.

## 1. The UI Layer

The UI layer contains everything users see and interact with. It is built with Jetpack Compose and Material 3 and includes ten screens: Home, Closet, Camera, Tagging, Spread, Vault, Archive, Sign-in, Profile, and Admin.

### Main Components and Libraries

- **Jetpack Compose:** Builds the application's screens and reusable UI components.
- **Material 3:** Provides consistent interface components, including cards, buttons, dialogs, snackbars, search fields, and filter chips.
- **Navigation Compose:** Uses `MystiqNavHost` to manage navigation between screens and pass data between destinations, such as a photo URI from Camera to Tagging.
- **AndroidX Activity:** Provides the `MainActivity` host for the Compose UI and supports activity-related functionality, including activity results.
- **CameraX:** Provides camera functionality and a live preview on the Camera screen.
- **Coil:** Loads and displays clothing images in Compose UI components, such as clothing cards and outfit previews.
- **Lifecycle ViewModel:** Separates screen-related state and UI logic from the interface.
- **Lifecycle Runtime Compose:** Supports lifecycle-aware collection of UI state, such as collecting a Flow using `collectAsStateWithLifecycle()`.
- **Kotlin Coroutines and Flow:** Support asynchronous operations and reactive data updates.
- **AndroidX Core:** Provides Android compatibility utilities where required by the application.
- **Dialogs and Snackbars:** Display warning, error, and success messages according to the style guide.

Each screen has its own ViewModel, such as `ClosetViewModel` or `TaggingViewModel`. Screens are responsible for displaying information and forwarding user actions to their ViewModels rather than implementing business logic directly.

ViewModels expose screen state through a `UiState` object representing loading, success, or error states. Compose observes these state updates and redraws the interface when the state changes. ViewModels also help preserve screen-related state during configuration changes, such as screen rotation.

## 2. The Domain Layer

The Domain layer contains the application's business rules and is written in plain Kotlin. It does not depend on Android-specific APIs or database implementation details.

### 2.1 Domain Models

The main domain models represent the application's real-world objects:

- `ClothingItem`
- `Outfit`, which can have a `SAVED` or `DRAFT` status
- `Category`
- `WearLog`
- `User`

### 2.2 Use Cases

Each use case is responsible for a specific application operation. Examples include:

- `AddClothingItemUseCase`
- Saving an outfit as a draft
- Searching the closet
- Logging that a clothing item was worn
- Retrieving saved outfits and drafts

ViewModels call the relevant use cases to perform business operations instead of implementing those operations directly.

### 2.3 Repository Interfaces

The Domain layer declares repository interfaces that define the operations required by the application:

- `ClosetRepository`
- `OutfitRepository`
- `WearRepository`
- `AuthRepository`

These interfaces specify what the application needs, such as saving a clothing item or retrieving drafts, without defining how the operation is implemented. This keeps business rules independent of specific storage technologies.

## 3. The Data Layer

The Data layer handles data storage, retrieval, and communication with the underlying data sources. It provides implementations of the repository interfaces declared in the Domain layer.

### Main Components and Libraries

- **Repository Implementations:** Implement `ClosetRepository`, `OutfitRepository`, `WearRepository`, and `AuthRepository`.
- **Room Database:** Stores structured local data, including clothing items, categories, outfits, drafts, tags, and wear history, through database entities and DAOs.
- **DataStore:** Stores lightweight application preferences, such as relevant sign-in or settings information.
- **ImageStorage:** Manages the storage and retrieval of clothing photos on the device.
- **AndroidX Palette:** Analyzes clothing images to identify dominant colors, supporting the clothing color statistics feature on the Spread screen.
- **WorkManager:** Schedules background tasks, such as wear-tracking reminders, when this functionality is implemented.
- **Mappers:** Convert database entities into domain models so that upper layers do not depend on database-specific details.
- **Kotlin Coroutines and Flow:** Support asynchronous data operations and the observation of data changes.

Room can expose database queries as Flow streams. When stored data changes, the relevant Flow can emit updated values, allowing the UI to display current information through its ViewModel.

## 4. Dependency Injection with Hilt

Hilt provides dependency injection for the application's components. It helps provide dependencies such as repository implementations and use cases to the ViewModels and other components that require them.

This reduces manual dependency creation and helps keep component construction organized.

The intended dependency flow is:

**UI → Domain ← Data implementations**

The UI depends on Domain operations, while the Data layer implements the repository interfaces defined by Domain. Hilt provides the configured implementations to the components that need them.

## 5. How the Components Interact

User actions move from the UI through the Domain layer to the Data layer. Results return to the UI through the relevant state and data-observation mechanisms.

### Example: Adding a Clothing Item

1. The user captures a clothing photo using the Camera screen and CameraX.
2. Navigation passes the photo URI to the Tagging screen.
3. The user enters the item's name, category, color, and brand.
4. When the user taps **Add Item**, the screen forwards the action to `TaggingViewModel`.
5. The ViewModel checks whether a similar item name already exists. If a duplicate is detected, the UI displays a warning dialog.
6. If the item passes validation, the ViewModel calls `AddClothingItemUseCase`.
7. The use case applies the relevant business rules and calls the appropriate `ClosetRepository` operation.
8. The repository implementation manages the photo through `ImageStorage`, uses Palette to extract its dominant color when required, and stores the item's structured data in Room.
9. Room emits updated data through Flow when the database changes.
10. The ViewModel exposes the updated information through `UiState`. Compose redraws the Closet screen, and a success snackbar confirms that the item was saved.

Coil can then load and display the stored clothing image in the appropriate UI component.

### Other Features

The same general architecture supports other features, including saving outfits, managing drafts in the Vault, searching the closet, recording clothing usage, and displaying clothing statistics on the Spread screen.

The general operation flow is:

**Screen → ViewModel → Use Case → Repository Interface → Repository Implementation → Data Source**

The result is observed or returned through the appropriate data and state mechanisms, allowing the UI to display updated information.

## 6. Benefits of This Architecture

The layered architecture provides several benefits:

- **Separation of concerns:** Each layer has a clearly defined responsibility.
- **Maintainability:** Changes to storage implementation can be isolated from business rules and UI components.
- **Testability:** Domain use cases can be tested independently of Android UI components and database implementations.
- **Reusability:** Shared components and use cases can support multiple screens.
- **Responsive UI:** Coroutines and Flow support asynchronous operations and reactive data updates.
- **Extensibility:** New features can be added while preserving the separation between presentation, business logic, and data management.

## 7. Architecture Diagram

The architecture diagram is provided separately in `architecture.png`.
