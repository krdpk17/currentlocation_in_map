# Current Location Map Android App

An Android application that displays the current location of the device on Google Maps with a modern Material Design interface.

## Features

- **Real-time Location Display**: Shows your current location on Google Maps
- **Location Information**: Displays latitude, longitude, and accuracy
- **Permission Handling**: Properly requests and handles location permissions
- **Modern UI**: Material Design 3 with floating action button and info card
- **Refresh Functionality**: Tap the refresh button to update your location
- **Dark Mode Support**: Automatically adapts to system theme

## Prerequisites

Before running this app, you need:

1. **Android Studio** (latest version recommended)
2. **Google Maps API Key** - Get one from [Google Cloud Console](https://console.cloud.google.com/)
3. **Android Device or Emulator** with Google Play Services

## Setup Instructions

### 1. Get Google Maps API Key

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select an existing one
3. Enable the **Maps SDK for Android** API
4. Create credentials (API Key)
5. Copy the API key

### 2. Configure API Key

1. Open `local.properties` file
2. Replace `YOUR_GOOGLE_MAPS_API_KEY_HERE` with your actual API key:
   ```
   MAPS_API_KEY=your_actual_api_key_here
   ```

### 3. Build and Run

1. Open the project in Android Studio
2. Sync the project with Gradle files
3. Connect your Android device or start an emulator
4. Click the "Run" button (green play icon)

## Permissions

The app requires the following permissions:

- `ACCESS_FINE_LOCATION`: For precise location data
- `ACCESS_COARSE_LOCATION`: For approximate location data
- `INTERNET`: For Google Maps functionality
- `ACCESS_NETWORK_STATE`: For network connectivity checks

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/currentlocationmap/
│   │   └── MainActivity.kt          # Main activity with location logic
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml    # Main UI layout
│   │   ├── values/
│   │   │   ├── strings.xml          # String resources
│   │   │   ├── colors.xml           # Color definitions
│   │   │   └── themes.xml           # App themes
│   │   └── drawable/
│   │       └── ic_refresh.xml       # Refresh icon
│   └── AndroidManifest.xml          # App manifest with permissions
└── build.gradle                     # App-level dependencies
```

## Key Components

### MainActivity.kt
- Handles location permissions using `ActivityResultContracts`
- Integrates Google Maps with `OnMapReadyCallback`
- Uses `FusedLocationProviderClient` for location updates
- Displays location marker and information

### Layout (activity_main.xml)
- `SupportMapFragment` for Google Maps display
- Material Design card for location information
- Floating action button for refresh functionality

## Troubleshooting

### Common Issues

1. **"Google Maps API key not found"**
   - Make sure you've added your API key to `local.properties`
   - Ensure the API key has Maps SDK for Android enabled

2. **"Location permission denied"**
   - The app will request location permissions on first run
   - Grant permissions in Settings if denied

3. **"Unable to get current location"**
   - Ensure location services are enabled on your device
   - Check if you're outdoors or have good GPS signal

4. **Build errors**
   - Sync project with Gradle files
   - Clean and rebuild the project
   - Update Android Studio and dependencies

## Dependencies

- **Google Play Services Maps**: For Google Maps integration
- **Google Play Services Location**: For location services
- **AndroidX Lifecycle**: For lifecycle management
- **Material Design Components**: For modern UI components

## License

This project is open source and available under the MIT License.

## Contributing

Feel free to submit issues and enhancement requests! 