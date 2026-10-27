# ⚡ RCV Calculator (Resistor Color Value Calculator)

[![Platform](https://img.shields.io/badge/Platform-Android-3DDC84.svg?style=flat&logo=android)](https://www.android.com/)
[![Language](https://img.shields.io/badge/Language-Java%208-ED8B00.svg?style=flat&logo=java)](https://www.oracle.com/java/)
[![Min SDK](https://img.shields.io/badge/Min%20SDK-21%20(Lollipop)-blue.svg?style=flat)]()
[![Target SDK](https://img.shields.io/badge/Target%20SDK-30%20(Android%2011)-brightgreen.svg?style=flat)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**RCV Calculator** is a high-precision, intuitive native Android application engineered for electrical engineering students, electronics hobbyists, hardware makers, and repair technicians. The app eliminates manual lookup tables and tedious color chart math by delivering a seamless, bidirectional calculation engine between standard resistor color bands and numerical resistance values.

---

## 🎯 Key Features

### 🔄 Bidirectional Calculation Engine
* **Color to Value Mode:** Select band colors via custom visual spinners featuring real color swatches to instantly compute exact resistance (in $\Omega$, $\text{k}\Omega$, $\text{M}\Omega$, $\text{G}\Omega$), tolerance ($\pm\%$), and temperature coefficients ($\text{ppm/}^\circ\text{C}$).
* **Value to Color Mode (Reverse Lookup):** Enter any target resistance value, select the unit multiplier, and let the algorithm determine the exact color band sequence required for your circuit.

### 🎨 Live Interactive Resistor Emulation
* Real-time vector-styled graphical resistor updates immediately as colors or values are selected.
* Allows side-by-side visual comparison with physical components on your workbench to eliminate band orientation errors.

### 📊 Full 4, 5, and 6-Band Support
* **4-Band:** Standard through-hole resistors (2 significant digits + multiplier + tolerance).
* **5-Band:** Precision metal-film resistors (3 significant digits + multiplier + tolerance).
* **6-Band:** High-precision resistors (3 significant digits + multiplier + tolerance + TCR $\text{ppm/}^\circ\text{C}$).

### ⚡ Smart Engineering Unit Formatting
* Automatic metric normalization handling values from fractional ohms ($< 1\Omega$) up to Giga-ohms ($\text{G}\Omega$).
* Formats decimal places cleanly for easy readability on lab benches.

---

## 🛠 Tech Stack & Architecture

| Component | Specification |
|---|---|
| **Language** | Java 8 |
| **Minimum SDK** | API 21 (Android 5.0 Lollipop) |
| **Target SDK** | API 30 (Android 11) |
| **Build System** | Gradle 6.5 / Android Gradle Plugin 4.1.2 |
| **Architecture** | Native Android Activities & Custom BaseAdapters |
| **UI Components** | AndroidX, Material Design Components, ConstraintLayout |
| **Performance** | Optimized with ViewHolder pattern on all custom dropdown spinners |
| **Monetization** | Integrated Unity Ads SDK with auto-reloading banner placement |

---

## 📂 Project Structure

```plaintext
RCCCalculator/
├── app/
│   ├── src/main/
│   │   ├── java/com/methun/rcvcalculator/
│   │   │   ├── MainActivity.java      # Color-to-Value calculator logic & visual band binding
│   │   │   ├── Value_to_Color.java    # Value-to-Color reverse calculation & band synthesis
│   │   │   ├── Menu_Page.java         # Mode selection dashboard & feedback integration
│   │   │   ├── splash_screen.java     # App startup splash screen
│   │   │   ├── spinnner_adaptor.java  # Optimized ViewHolder adapter for color spinners
│   │   │   └── icn_adapter.java       # Custom adapter for unit multiplier dropdowns
│   │   ├── res/
│   │   │   ├── layout/                # Responsive XML layouts & vector resistor views
│   │   │   ├── drawable/              # Color swatches and vector graphic assets
│   │   │   └── values/                # Strings, colors, styles, and themes
│   │   └── AndroidManifest.xml
│   ├── build.gradle                   # App module build configuration & ProGuard rules
├── unity-ads/                         # Unity Ads library integration module
├── gradle/wrapper/                    # Gradle wrapper binaries & properties
├── build.gradle                       # Root build configuration
├── settings.gradle                    # Project settings
└── .gitignore                         # Standard Android / macOS version control ignores
```

---

## 🚀 Getting Started & Building

### Prerequisites
* **Android Studio** (Electric Eel or newer recommended)
* **JDK 8** or higher
* **Android SDK** with platform tools for API 30+

### Clone & Build

1. **Clone the repository:**
   ```bash
   git clone https://github.com/methun404/RCV-Calculator.git
   cd RCV-Calculator
   ```

2. **Open in Android Studio:**
   * Open Android Studio, select **Open**, and navigate to the cloned project folder.
   * Allow Gradle to sync the project dependencies.

3. **Build via Command Line:**
   ```bash
   # Debug APK
   ./gradlew assembleDebug

   # Release Bundle (AAB)
   ./gradlew bundleRelease
   ```

---

## 📑 Resistor Color Code Quick Reference

| Color | Digit Value | Multiplier | Tolerance | Temp. Coeff (TCR) |
|---|:---:|:---:|:---:|:---:|
| **Black** | 0 | $1\Omega$ | - | $250\text{ ppm/}^\circ\text{C}$ |
| **Brown** | 1 | $10\Omega$ | $\pm 1\%$ | $100\text{ ppm/}^\circ\text{C}$ |
| **Red** | 2 | $100\Omega$ | $\pm 2\%$ | $50\text{ ppm/}^\circ\text{C}$ |
| **Orange** | 3 | $1\text{ k}\Omega$ | $\pm 0.05\%$ | $15\text{ ppm/}^\circ\text{C}$ |
| **Yellow** | 4 | $10\text{ k}\Omega$ | $\pm 0.02\%$ | $25\text{ ppm/}^\circ\text{C}$ |
| **Green** | 5 | $100\text{ k}\Omega$ | $\pm 0.5\%$ | $20\text{ ppm/}^\circ\text{C}$ |
| **Blue** | 6 | $1\text{ M}\Omega$ | $\pm 0.25\%$ | $10\text{ ppm/}^\circ\text{C}$ |
| **Violet** | 7 | $10\text{ M}\Omega$ | $\pm 0.1\%$ | $5\text{ ppm/}^\circ\text{C}$ |
| **Grey** | 8 | $100\text{ M}\Omega$ | $\pm 0.01\%$ | $1\text{ ppm/}^\circ\text{C}$ |
| **White** | 9 | $1\text{ G}\Omega$ | - | - |
| **Gold** | - | $0.1\Omega$ | $\pm 5\%$ | - |
| **Silver** | - | $0.01\Omega$ | $\pm 10\%$ | - |

---

## 🤝 Contributing

Contributions, feature requests, and bug reports are welcome!
Feel free to open an issue or submit a pull request.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'feat: Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

Distributed under the **MIT License**. See [`LICENSE`](LICENSE) for more details.
