🍅

# Automato - DOM Test Micro System

A basic infrastructure for self-contained, automated website testing built on the Java runtime. It uses Selenium WebDriver to drive a real Chrome browser, and includes a lightweight test runner, setup/teardown lifecycle, screenshot capture, and OS detection — no external test framework orchestration required.

---

## Project Structure

```
seleniumExercise/
├── drivers/
│   ├── chromedriver          # ChromeDriver binary for macOS
│   └── chromedriver.exe      # ChromeDriver binary for Windows
├── screenshots/              # Screenshots saved here during test runs
├── src/main/java/
│   ├── com/company/
│   │   ├── Main.java         # Entry point
│   │   └── TestManager.java  # Orchestrates test scenarios
│   ├── common/
│   │   ├── Bootstrap.java    # Base class: driver + actions setup
│   │   ├── Setup.java        # ChromeDriver initialization per OS
│   │   ├── Actions.java      # Reusable browser actions (click, find, screenshot...)
│   │   └── TearDown.java     # Browser cleanup after scenarios/suite
│   ├── helpers/
│   │   └── OperatingSystemHelper.java
│   └── tests/
│       └── testsScenarios.java  # Test scenario implementations
└── pom.xml
```

---

## Prerequisites

- **Java 8+** — [Download](https://www.oracle.com/java/technologies/downloads/)
- **Chrome browser** installed
- **ChromeDriver** matching your Chrome version — already included in `drivers/` for Windows and macOS
- **Maven** (optional, only needed for the Maven-based workflow) — [Download](https://maven.apache.org/download.cgi)

To verify your Java installation:
```bash
java -version
```

---

## Installation

### 1. Clone or download the project

```bash
git clone <repository-url>
cd seleniumExercise
```

### 2. Match ChromeDriver to your Chrome version

Check your Chrome version at `chrome://settings/help` and replace the `drivers/chromedriver` or `drivers/chromedriver.exe` binary with the matching version from:
https://googlechromelabs.github.io/chrome-for-testing/

---

## Quick Test Run (Pre-built JAR)

The `target/` directory is included in this repository with a pre-built JAR and all dependencies. To run the tests immediately without any build step:

```bash
java -cp "target/seleniumExercise-1.0-SNAPSHOT.jar;target/dependency/*" com.company.Main
```

> On macOS/Linux replace `;` with `:` in the classpath.

---

## Running the Tests

### Option A — Pure Java (no Maven required)

This approach requires the dependency JARs to be present in `target/dependency/`. This is a one-time setup step.

**Step 1: Gather dependencies (one-time only)**

If you have Maven available:
```bash
mvn dependency:copy-dependencies
```

If you don't have Maven, manually download the following JARs and place them in `target/dependency/`:
- selenium-java 3.141.59
- testng 6.14.3
- junit 4.12
- commons-io 2.11.0
- guava 31.1-jre
- javax.inject 1

**Step 2: Compile the source (one-time, or after code changes)**

```bash
mvn compile
mvn package -DskipTests
```

Or manually compile with `javac` pointing to the dependency JARs.

**Step 3: Run**

```bash
java -cp "target/seleniumExercise-1.0-SNAPSHOT.jar;target/dependency/*" com.company.Main
```

> On macOS/Linux use `:` instead of `;` as the classpath separator:
> ```bash
> java -cp "target/seleniumExercise-1.0-SNAPSHOT.jar:target/dependency/*" com.company.Main
> ```

---

### Option B — With Maven

**Build and run in one step:**

```bash
mvn package -DskipTests
java -cp "target/seleniumExercise-1.0-SNAPSHOT.jar;target/dependency/*" com.company.Main
```

Or use the Maven exec plugin:

```bash
mvn compile exec:java -Dexec.mainClass="com.company.Main"
```

---

## What the Tests Do

| Scenario | Description |
|---|---|
| `ScenarioGamespotMasthead` | Navigates to gamespot.com and asserts the main navigation masthead is present |
| `ScenarioGamespotReviews` | Navigates to the reviews page, captures a screenshot, clicks the first review link, and asserts a page title is returned |

Screenshots are saved to the `screenshots/` directory with a timestamp in the filename.

---

## Supported Operating Systems

| OS | ChromeDriver |
|---|---|
| Windows | `drivers/chromedriver.exe` |
| macOS | `drivers/chromedriver` |

Linux is not currently supported.
