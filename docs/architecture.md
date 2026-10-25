# Mystiq Application Architecture

Mystiq is built in three layers: **UI, Domain, and Data**. Hilt connects them, and each layer only talks to the next layer. The UI never accesses the database directly, and the Domain layer does not depend on Android.

## 1. The UI Layer

This layer contains everything the user sees and interacts with. It is built with Jetpack Compose and Material 3 and contains ten screens: Home, Closet, Camera, Tagging, Spread, Vault, Archive, Sign-in, Profile, and Admin.

- **MystiqNavHost (Navigation Compose):** Decides which screen is displayed and passes data between screens, such as the photo URI from Camera to Tagging.
- **MainActivity:** Hosts the Compose UI.
- **CameraX:** Provides the live camera preview on the Camera screen.
- **Dialogs and Snackbars:** Display error, warning, and success messages according to the style guide.

Each screen has its own ViewModel, such as `ClosetViewModel` or `TaggingViewModel`. Screens contain no business logic. When users tap or type, the screen passes the action to its ViewModel. When the ViewModel publishes a new state, the screen redraws.

The ViewModel holds the screen state in a `UiState` object, which represents loading, success, or error. It also survives screen rotation, helping users retain their current state.

## 2. The Domain Layer

This layer contains the application's business rules, written in plain Kotlin.

### Domain Models

The models represent the main objects in the application:

- `ClothingItem`
- `Outfit`, which can be `SAVED` or `DRAFT`
- `Category`
- `WearLog`
- `User`

### Use Cases

Each use case is a small class responsible for one task, such as adding an item, saving an outfit as a draft, searching the closet, or logging that an item was worn.

ViewModels call these use cases instead of implementing business operations directly.

### Repository Interfaces

The Domain layer declares the repository interfaces:

- `ClosetRepository`
- `OutfitRepository`
- `WearRepository`
- `AuthRepository`

An interface defines what the application needs, such as saving an item or retrieving all drafts, without specifying how the operation is performed. This means the use cases do not depend on a specific database, allowing the storage implementation to change without modifying the rest of the application.

## 3. The Data Layer

This layer handles the actual storage and retrieval of data.

- **Repository Implementations:** Implement the interfaces declared in the Domain layer.
- **Room Database:** Stores clothing items, categories, outfits, drafts, tags, and wear history through its DAOs.
- **DataStore:** Stores lightweight settings, such as information about the signed-in user.
- **ImageStorage:** Saves clothing photos on the device.
- **Palette:** Analyzes clothing photos to identify dominant colors, supporting the clothing color statistics feature on the Spread screen.
- **WorkManager:** Runs background workers for wear-tracking reminders, even when the application is closed.
- **Mappers:** Convert database entities into Domain models so that upper layers do not depend on database-specific details.

## 4. How the Components Interact

Information moves through the architecture in a controlled way: user actions flow downward, while data and results flow back upward.

### Example: Adding a Clothing Item

1. The user takes a photo on the Camera screen, and CameraX captures it.
2. Navigation passes the user to the Tagging screen and sends the photo URI.
3. The user enters the item's name, category, color, and brand.
4. When the user taps **Add Item**, the screen sends the action to `TaggingViewModel`.
5. The ViewModel checks whether a similar item name already exists. If a duplicate is detected, a warning dialog is displayed.
6. If no duplicate is found, the ViewModel calls `AddClothingItemUseCase`.
7. The use case creates a `ClothingItem` and passes it to `ClosetRepository`.
8. The repository implementation stores the photo, extracts its dominant color, and inserts the item into Room.
9. Room exposes the updated data through a Flow, allowing the Closet screen's data to update automatically.
10. The ViewModel publishes a new `UiState`, the screen redraws, and a success snackbar confirms that the item was saved.

This process does not require the user to refresh the screen manually.

### Other Features

The same interaction pattern applies to other features, including saving outfits in the preview space, moving drafts to the Vault, and viewing statistics on the Spread screen.

Each feature follows the same general flow:

**Screen → ViewModel → Use Case → Repository → Data Source**

The result then returns through the data observation and UI state mechanisms, allowing the screen to display the updated information.

## 5. Benefits of This Architecture

This structure keeps responsibilities separate and allows team members to work on different layers with fewer dependencies between them.

It also improves testability because individual use cases can be tested independently of the UI and database.

By separating presentation, business rules, and data management, Mystiq becomes easier to maintain, test, and extend.


