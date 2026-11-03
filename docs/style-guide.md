# Mystiq — Style Guide

## 1. Overview

This style guide defines the visual design standards for Mystiq, an Android digital wardrobe application. It ensures consistency across all screens, including Home, Closet, Camera, Tagging, Spread, Vault, Archive, Sign-in, and Profile.

The design uses a coordinated palette of deep plum, soft pink, warm cream, and muted mauve to create a consistent visual identity.

## 2. Color Palette

### 2.1 Primary Colors

| Color Name | HEX Code | Usage |
|---|---|---|
| Deep Plum | `#3A2C39` | Primary dark backgrounds and prominent visual elements |
| Soft Pink | `#F2D9ED` | Light backgrounds, cards, and secondary surfaces |
| Warm Cream | `#F7E5A9` | Accent elements and highlights |
| Muted Mauve | `#896674` | Secondary backgrounds and supporting elements |

### 2.2 Status Colors

| Status | HEX Code | Usage |
|---|---|---|
| Success | `#2E7D32` | Successful item or outfit saving and completed actions |
| Warning | `#B7791F` | Warnings, such as similarly named clothing items |
| Error | `#B3261E` | Failed saving, validation errors, and operation failures |

Status colors should be accompanied by clear text or icons so that users do not have to rely on color alone.

*Note: The status color HEX values are proposed values and should be confirmed against the final interface designs.*

## 3. Typography

Mystiq uses the Roboto sans-serif typeface for readability and consistency with Android interface conventions.

| Text Style | Font Size | Usage |
|---|---|---|
| Heading 1 | 24 sp | Main screen titles and prominent headings |
| Heading 2 | 20 sp | Section headings and important screen content |
| Body Text | 16 sp | Clothing details, descriptions, and primary content |
| Labels and Supporting Text | 14 sp | Form labels, secondary information, and helper text |

Font sizes use Android scale-independent pixels (sp). Text should remain readable and maintain a clear visual hierarchy across screens.

## 4. Spacing and Shapes

Mystiq uses density-independent pixels (dp) for layout dimensions and spacing.

| Element | Specification |
|---|---|
| Base spacing unit | 8 dp |
| Screen margins | 16 dp |
| Card inner padding | 12 dp |
| Card corner radius | 12 dp |
| Dialog corner radius | 12 dp |
| Button corner radius | 8 dp |
| Chip corner radius | 8 dp |

Spacing and corner radii should be applied consistently across all application screens.

## 5. UI Components

### 5.1 Clothing Cards

Clothing cards display an item image, name, category, and other relevant information. Cards should use consistent spacing, corner radii, and image proportions.

### 5.2 Outfit Cards

Saved outfit cards display outfit thumbnails and relevant information. Their appearance should remain consistent with clothing cards.

### 5.3 Buttons

Buttons should use consistent typography, shapes, and colors.

- **Primary actions:** Adding clothing items and saving outfits.
- **Secondary actions:** Editing information and applying filters.
- **Destructive actions:** Deleting outfits, with confirmation required.

### 5.4 Navigation

The bottom navigation bar should provide consistent access to the main application sections: Closet, Outfits, Add Item, and Profile.

Navigation labels and icons should remain consistent across screens.

### 5.5 Search Fields and Filter Chips

Search fields allow users to find clothing items by name. Filter chips represent available categories or filtering options and should have consistent spacing and selected states.

### 5.6 Dialogs and Snackbars

Dialogs are used for important decisions, such as confirming outfit deletion.

Snackbars provide brief feedback after actions, such as successful saving or logging out.

## 6. UI States and Feedback

| UI State | Expected Behavior |
|---|---|
| Loading | Indicate that content is being loaded. |
| Empty Closet | Display "Your closet is empty" and an action to add the first item. |
| No Matching Items | Explain that no items match the current search or filters. |
| Camera Permission Denied | Explain why camera access is unavailable and provide an alternative or settings action. |
| Save Error | Display an error message and offer Retry or Cancel while preserving unsaved edits where possible. |
| Delete Confirmation | Ask the user to confirm or cancel before deleting an outfit. |
| Success | Confirm successful actions with a suitable message or snackbar. |
| Selected State | Clearly distinguish selected items and active filters. |

## 7. Forms and Account Screens

### 7.1 Sign-in Screen

The Sign-in screen should use consistent text fields, password input, buttons, and supporting links. The Forgot Password link should be visually distinguishable from the main sign-in action.

A logout confirmation snackbar should appear briefly when the user returns to the Sign-in screen after logging out.

### 7.2 Profile Screen

The Profile screen should display the user's name, email address, saved clothing item count, selected body shape, settings, preferences, and logout action.

The Edit Information and Settings actions should follow the same button and typography conventions used throughout the application.

## 8. Accessibility and Consistency

- Maintain readable text sizes and sufficient contrast between text and backgrounds.
- Use consistent colors, typography, spacing, and component shapes across all screens.
- Provide clear labels for buttons and interactive elements.
- Communicate errors and warnings through text or icons as well as color.
- Keep interaction feedback clear and predictable.
- Use appropriate content descriptions for meaningful images and icons.

## 9. Maintenance

This document should be updated whenever the team approves changes to the color palette, typography, spacing, shared components, or UI states.

All wireframes and implemented screens should follow the same approved design standards.
