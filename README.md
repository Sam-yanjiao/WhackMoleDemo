# Whack-a-Mole
This is a demo of a WhackMole game
Whack-a-Mole Game
A simple Android game built with Kotlin in Android Studio, featuring a classic Whack-a-Mole gameplay experience with a leaderboard and background music.
Overview
Whack-a-Mole is a single-player game where players tap moles that randomly pop up on a 4x4 grid. The goal is to score as many points as possible within 60 seconds. The game includes a persistent leaderboard to track top scores and background music to enhance the experience.
Features

Gameplay: Tap moles to score points within a 60-second timer.
Pause/Resume: Pause and resume the game at any time.
Leaderboard: Save and view the top 10 scores using SQLite.
High Score: Tracks the highest score using SharedPreferences.
Background Music: Looping audio during gameplay (requires a background_music.mp3 file in res/raw).
Navigation: Main menu, game screen, score screen, and leaderboard screen.

Screenshots
(Add screenshots here by uploading images to the repository and linking them, e.g., ![Main Menu](screenshots/main_menu.png))
Prerequisites

Android Studio: Version 2023.1.1 or later (e.g., Android Studio Iguana).
Kotlin: Version 1.9.x or compatible.
Minimum SDK: API 21 (Android 5.0 Lollipop).
Git: For cloning and contributing.

Sync Project:
Click Sync Project with Gradle Files to download dependencies.


Add Background Music:
Place a background_music.mp3 file in app/src/main/res/raw/ (create the raw folder if it doesn't exist).
Recommended: Use a small (<1MB), royalty-free MP3 file.


Run the App:
Connect an Android device or start an emulator.
Click the Run button (green triangle) in Android Studio.



Project Structure

app/src/main/java/com/example/whackmole/:
MainActivity.kt: Main menu with "Play" and "Leaderboard" buttons.
GameActivity.kt: Core gameplay with grid, timer, and music.
ScoreActivity.kt: Displays score and saves to leaderboard.
LeaderboardActivity.kt: Shows top 10 scores.
DatabaseHelper.kt: SQLite database for leaderboard.
LeaderboardAdapter.kt: RecyclerView adapter for leaderboard.
GameControlsFragment.kt: Fragment for game controls (pause/home).


app/src/main/res/layout/:
Layout XML files for each activity and leaderboard items.


app/src/main/res/raw/:
background_music.mp3 (user-provided audio file).



Dependencies
Defined in app/build.gradle:
dependencies {
    implementation 'androidx.recyclerview:recyclerview:1.2.1'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4'
}

How to Play

Launch the app and click "Play" from the main menu.
Tap moles as they appear on the grid to score points.
Pause the game using the "Pause" button; resume or return to the main menu with "Home."
After 60 seconds, enter your name on the score screen to save your score.
View the leaderboard from the main menu or score screen.

Contributing

Fork the repository.
Create a new branch (git checkout -b feature/your-feature).
Commit your changes (git commit -m "Add your feature").
Push to your fork (git push origin feature/your-feature).
Open a

