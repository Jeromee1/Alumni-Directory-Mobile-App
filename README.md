# Alumni Directory

An alumni networking platform with approval-gated access, built with Firebase. Features alumni profile browsing and an admin dashboard for user management and platform statistics.

---

## Features

### User
- Register and await admin approval before gaining access
- Browse alumni profiles
- Customize profile (profile image, details)
- View detailed profile pages

### Admin
- Approve or reject user registrations
- Manage existing users
- View platform statistics via admin dashboard

---

## Screenshots

| Login | Profile Browsing | Admin Dashboard |
|-------|-----------------|-----------------|
| <img src="https://i.imgur.com/oJezSek.jpeg" width="250"/> | <img src="https://i.imgur.com/dw6Wddd.jpeg" width="250"/> | <img src="https://i.imgur.com/zxejUIy.jpeg" width="250"/> |

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| UI | Jetpack Compose |
| Auth | Firebase Authentication |
| Database | Cloud Firestore |
| Build | Gradle |

---

## Setup

### Prerequisites
- Android Studio (latest stable)
- A Firebase account

### Steps

1. **Create a Firebase project**
   - Go to [Firebase Console](https://console.firebase.google.com/) and create a new project

2. **Register your Android app**
   - In the Firebase project, add an Android app
   - Use the package name matching the one in `app/build.gradle`

3. **Enable services**
   - Enable **Firestore Database** (start in test mode or configure rules)
   - Enable **Firebase Authentication** and your preferred sign-in methods

4. **Download config file**
   - Download `google-services.json` from the Firebase project settings

5. **Place the config file**
   ```
   AlumniDirectory/
   └── app/
       └── google-services.json   ← place it here
   ```

6. **Build and run**
   - Open the project in Android Studio
   - Let Gradle sync
   - Run on an emulator or physical device (API 24+)

---

## Contributors

| Role | GitHub |
|------|--------|
| Frontend & UX | [@Jeromee1](https://github.com/Jeromee1) |
| Backend | [@Soulstriderx](https://github.com/Soulstriderx) |
