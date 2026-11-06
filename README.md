# CRACK QL — Kotlin Android project

Package: `com.crackxq.modmenu`

This is an **offline training/demo app**. It draws locally generated dummy targets and demonstrates UI toggles for a head-lock-style reticle and ESP-style markers. It does not connect to CrossFire, inspect its memory, inject code, alter gameplay, or bypass anti-cheat.

## Build from Termux (no Android Studio)

This project includes a GitHub Actions build workflow. Termux is used to upload the source and download the compiled APK; GitHub's Android build runner performs the Android SDK/Gradle build.

1. Install tools:
   ```sh
   pkg update -y
   pkg install git gh unzip -y
   ```
2. Extract the project ZIP and enter its folder:
   ```sh
   termux-setup-storage
   cd ~/storage/downloads
   unzip -o CRACK_QL_Kotlin_Project.zip
   cd CRACK_QL_Kotlin
   ```
   If you extracted it elsewhere, `cd` to that folder instead.
3. Sign in to GitHub:
   ```sh
   gh auth login
   ```
4. Create a GitHub repository and upload the project:
   ```sh
   git init
   git add .
   git commit -m "Initial CRACK QL Android project"
   git branch -M main
   gh repo create CRACK_QL --public --source=. --remote=origin --push
   ```
5. Wait for the build:
   ```sh
   gh run list
   gh run watch
   ```
6. Download the APK artifact into the current folder:
   ```sh
   gh run download --name CRACK-QL-debug-apk
   ```
   The APK will be inside the downloaded artifact folder. Find it with:
   ```sh
   find . -name "*.apk"
   ```

If Git reports that your identity is unknown, set it once:
```sh
git config --global user.name "CRACK QL"
git config --global user.email "YOUR_GITHUB_EMAIL"
```

## Device
The app targets Android 6.0+ (minSdk 23). No root permission is requested or needed for this demo.
