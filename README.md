# 🎵 Genre Classifier

An Android application that uses machine learning to classify music genres in real-time. Built with Kotlin and TensorFlow Lite, this app demonstrates advanced audio processing and ML model integration in Android.

## 🚀 Features

- **Real-time Genre Classification**: Identifies music genres using TensorFlow Lite
- **Support for Multiple Genres**: 
  - Disco
  - Classical
  - Country
  - Blues
  - Metal
  - Rock
  - Reggae
- **Local Database Storage**: Caches classification results using Realm
- **Clean Architecture**: Implements MVVM pattern with modular design
- **Modern Android Development**: Uses Jetpack Compose for UI

## 🛠️ Technology Stack

- **Language**: Kotlin 1.6.10
- **UI Framework**: Jetpack Compose 1.1.1
- **Architecture Components**:
  - ViewModel
  - LiveData
  - Navigation
- **Dependency Injection**: Koin 3.2.0
- **Database**: Realm 10.11.1
- **ML Framework**: TensorFlow Lite
- **Audio Processing**: Custom Librosa implementation
- **Build System**: Gradle 7.2.1

## 📦 Project Structure

```
.
├── app/                  # Application module
├── core/                 # Core functionality module
├── genreclassifier/     # Genre classification module
└── librosa/             # Audio processing module
```

### Module Details

1. **App Module**: 
   - Main application components
   - UI implementation
   - Navigation setup

2. **Core Module**:
   - Common utilities
   - Base classes
   - Shared interfaces

3. **Genre Classifier Module**:
   - TensorFlow Lite integration
   - Genre classification logic
   - Result processing

4. **Librosa Module**:
   - Audio processing utilities
   - Feature extraction
   - Signal processing

## 🏗️ Architecture

The project follows Clean Architecture principles with MVVM pattern:

```
├── data/
│   ├── repository/      # Data management
│   └── cache/          # Local storage
├── domain/
│   └── model/          # Business models
└── presentation/
    └── ui/             # User interface
```

### Key Components

1. **Genre Classifier**:
```kotlin
class GenreClassifier(ctx: Context) {
    companion object {
        private const val MODEL_PATH = "genre_classifier1000.tflite"
        val predictionLabels = arrayOf(
            "disco", "classical", "country", "blues",
            "metal", "rock", "reggae"
        )
    }
    // Implementation
}
```

2. **Database Integration**:
```kotlin
class DatabaseHelper(context: Context) {
    private val config: RealmConfiguration
    init {
        Realm.init(context)
        config = RealmConfiguration.Builder()
            .allowQueriesOnUiThread(false)
            .allowWritesOnUiThread(false)
            .deleteRealmIfMigrationNeeded()
            .build()
    }
}
```

## 🔧 Setup and Installation

1. **Prerequisites**:
   - Android Studio Arctic Fox or newer
   - Android SDK 21 or higher
   - Kotlin 1.6.10 or higher

2. **Clone the Repository**:
```bash
git clone https://github.com/yourusername/GenreClassifier.git
```

3. **Build and Run**:
   - Open project in Android Studio
   - Sync Gradle files
   - Build and run on device/emulator

## 📱 Usage

1. **Grant Permissions**:
   - Audio recording
   - Storage access

2. **Select Audio Source**:
   - Record live audio
   - Choose from device storage

3. **View Results**:
   - Real-time genre classification
   - Historical classifications

## 🧪 Machine Learning Model

The app uses a custom TensorFlow Lite model trained on music genres:

- Input: Audio features extracted using Librosa
- Output: Genre probability distribution
- Model Size: Optimized for mobile devices
- Supported Formats: WAV, MP3

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## 📚 Documentation

For detailed documentation:
- [Setup Guide](docs/setup.md)
- [Architecture Details](docs/architecture.md)
- [ML Model Information](docs/ml-model.md)

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**Ahmed Mamdouh**
- GitHub: [@ahmedmamdouh13](https://github.com/ahmedmamdouh13)

## 🙏 Acknowledgments

- TensorFlow Lite team for ML tools
- Librosa team for audio processing
- Android Jetpack team for modern Android components
