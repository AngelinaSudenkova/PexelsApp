# PexelsApp

PexelsApp is an Android application that allows users to browse and download photos from the Pexels API. The app is built using Kotlin and Jetpack Compose, and it follows the MVVM architecture pattern.

## Features

- Browse photos by category
- Download photos to the gallery
- Save photos to bookmarks
- View detailed information about photos
- Search for photos
- Handle network errors gracefully

## Screens

- **Splash Screen**: Displays until the data for the home screen is loaded.
- **Home Screen**: Shows a list of photos and allows searching and browsing by category.
- **Detail Screen**: Displays detailed information about a selected photo.
- **Bookmark Screen**: Shows a list of bookmarked photos.

## Architecture

The app follows the Clean Architecture pattern, which divides the project into different layers:

- **Presentation Layer**: Contains UI components built using Jetpack Compose.
- **Domain Layer**: Contains business logic and use cases.
- **Data Layer**: Manages data sources, including remote APIs and local databases.

The MVVM (Model-View-ViewModel) architecture pattern is used within the Presentation Layer to manage UI-related data and handle business logic.

## Dependencies

- Jetpack Compose for building the UI
- Hilt for dependency injection
- Retrofit for network requests
- Room for local database
- Coil for image loading
- Coroutines for asynchronous programming

# Screens

## Splash Screen
| Dark Mode |
| --- |
| ![SplashScreen](https://github.com/user-attachments/assets/75160008-c58b-463e-a79f-c1e72abdf0cd) |

## Home Screen
| Dark Mode | Light Mode |
| --- | --- |
| ![HomeScreen](https://github.com/user-attachments/assets/a06a2c17-e711-42d5-9d82-e60de158e117) | ![HomeScreenLight](https://github.com/user-attachments/assets/05d75843-2084-48c2-bf8d-976587be63f1) |

## Loading & Connection Issues
| Loading Progress | No Internet Connection |
| --- | --- |
| ![ProgressBar](https://github.com/user-attachments/assets/0125d5ef-b2dd-4b34-83f4-7c4e16b919ec) | ![InternetConnectionIssues](https://github.com/user-attachments/assets/a5122ddd-86d5-403d-be7c-6a9ef1463ba1) |

## Search Results
| Dark Mode |
| --- |
| ![SearchResults](https://github.com/user-attachments/assets/8e54d125-2403-4fc5-97a1-04cccd4f85a9) |

## Details Screen
| Dark Mode | Light Mode |
| --- | --- |
| ![DetailsSdcreen](https://github.com/user-attachments/assets/580492c3-8ea3-4dd4-8b79-b70560cbe6bc) | ![DetailsScreenLight](https://github.com/user-attachments/assets/670e4812-f24f-4fb3-b433-c84b63d73407) |

## Marked Photos & Download Success
| Marked Photos | Download Complete |
| --- | --- |
| ![Marked Photos](https://github.com/user-attachments/assets/05befe3c-1f07-4f37-8802-325fefefad12) | ![DownoloadSuccess](https://github.com/user-attachments/assets/d581c699-dd0d-4383-812f-e9cf386326a9) |

## Bookmarks & No Bookmarks
| Dark Mode | Light Mode |
| --- | --- |
| ![Boomarks](https://github.com/user-attachments/assets/54251ef5-0158-4250-bfc1-db9454744a9f) | ![BookMarksLight](https://github.com/user-attachments/assets/cccb26d3-d032-44eb-a2c4-60f3e1d7332b) |

## No Bookmarks & No Data Available
| No Bookmarks | No Data Available |
| --- | --- |
| ![NoBookmarks](https://github.com/user-attachments/assets/ba53b43c-9078-4618-93ef-0f4a7b50ded4) | ![NoDataAvailable](https://github.com/user-attachments/assets/2993095b-3cbf-42ae-bc88-0cf5a4cadfe2) |

















- Kotlin 1.5 or later

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/AngelinaSudenkova/PexelsApp.git
