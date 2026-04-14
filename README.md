# HybridCore

A custom rendering engine mod for Minecraft Fabric, optimized for Android devices with Mali GPUs running via PojavLauncher.

## Features

- Custom rendering pipeline replacing Minecraft's default renderer
- OpenGL ES backend optimized for mobile GPUs
- Vulkan backend support (planned)
- Adaptive quality system for performance optimization
- Advanced chunk meshing with greedy algorithm
- Memory pooling for efficient resource management
- Telemetry system for performance monitoring
- Android-specific configuration profiles

## Project Structure

This is a multi-module Gradle project:

- `common/` - Shared code between platforms
- `fabric/` - Fabric mod implementation
- `buildSrc/` - Build logic and dependencies

## Building

1. Ensure you have Java 17 installed
2. Run `./gradlew build` in the HybridCore directory

## Installation

1. Build the mod JAR
2. Place in your mods folder
3. Launch Minecraft with Fabric loader

## Android Optimization

- Targets Mali GPUs commonly found in Android devices
- Uses PojavLauncher for running Fabric mods on Android
- GLES backend prioritizes compatibility and performance
- Adaptive quality adjusts based on device capabilities

## License

MIT