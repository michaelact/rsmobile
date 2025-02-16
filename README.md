# RS Mobile

> **⚠️ Note:**  
> RS Mobile is a demo project for a college assignment and is **not production-ready**.

## Overview

**RS Mobile** is a hospital finder application built using Kotlin and Jetpack Compose. It helps users find nearby hospitals based on their location and provides essential hospital information.

## Core Features

1. **Automatic Location Detection**
    - Detects user's city and coordinates
    - Uses IP-API for location services

2. **Hospital Finder**
    - Lists nearby hospitals based on user / IP location
    - Sorts hospitals by distance
    - Shows hospital details (name, address, phone)

3. **Instant Navigation**
    - Opens Google Maps for navigation to hospital
    - Shows hospital location on map

## Requirements

Before you begin, make sure you have the following tools:

1. **Android Studio**  
   The official development environment for Android apps.  
   👉 [Installation Guide](https://developer.android.com/codelabs/basic-android-kotlin-compose-install-android-studio)

2. **MapTiler API Key**  
   Required for geocoding and mapping features.  
   👉 [Get API Key](https://www.maptiler.com/)

## Getting Started

Follow these steps to set up and run the application:

1. **Clone the Repository**
   ```bash
   git clone https://github.com/michaelact/rsmobile
   cd rsmobile
   ```

2. **Configure API Key**  
   Create `local.properties` in project root:
   ```properties
   sdk.dir=/path/to/android/sdk
   MAPTILER_API_KEY=your_api_key_here
   ```

3. **Build and Run**  
   Open the project in Android Studio and run it on an emulator or device.

Watch this demo video to see how it works: https://youtube.com/shorts/Nq1GF_d8caI?feature=share
