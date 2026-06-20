# IGI Android Game

A tactical stealth-based first-person shooter game for Android, inspired by Project IGI.

## Features

- **Stealth Gameplay**: Avoid detection by enemies while completing missions
- **Mission-Based Structure**: Complete objectives to progress
- **Enemy AI**: Enemies patrol, detect, and chase the player
- **Dynamic Combat**: Fire weapons, aim, and manage ammunition
- **Mobile Controls**: On-screen joystick and action buttons
- **Real-time Gameplay**: ~60 FPS game loop

## Game Mechanics

### Player
- Move using the on-screen joystick (bottom-left)
- Fire weapons using the FIRE button (bottom-right)
- Toggle aim mode using the AIM button
- Maintain stealth to avoid detection
- Complete objectives to earn points

### Enemies
- **Patrol**: Walk predetermined paths when not alert
- **Detect**: Notice the player if they enter vision range
- **Chase**: Pursue and fire at detected players
- **Alert**: Turn red when suspicious or engaged

## Project Structure

```
igi-android/
├── app/
│   ├── src/main/
│   │   ├── java/com/igi/game/
│   │   │   ├── MainActivity.kt
│   │   │   ├── ui/
│   │   │   │   └── GameView.kt
│   │   │   ├── engine/
│   │   │   │   └── GameEngine.kt
│   │   │   └── models/
│   │   │       ├── Player.kt
│   │   │       ├── Enemy.kt
│   │   │       └── Mission.kt
│   │   ├── res/
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
└── settings.gradle.kts
```

## Getting Started

### Requirements
- Android Studio 2022.1 or later
- Android SDK API 24+
- Kotlin 1.9.0+
- JDK 1.8+

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Shahil-gupta-jpg/igi-android.git
   cd igi-android
   ```

2. Open the project in Android Studio

3. Build the project:
   ```bash
   ./gradlew build
   ```

4. Run on an emulator or device:
   ```bash
   ./gradlew run
   ```

## Controls

| Control | Action |
|---------|--------|
| Left Joystick | Move player |
| FIRE Button | Shoot weapon |
| AIM Button | Toggle aim mode |

## Mission Objectives

- Reach the objective area at coordinates (1000+, 500+)
- Avoid being detected by enemies
- Eliminate threats if necessary
- Manage ammunition carefully

## Future Enhancements

- [ ] Multiple weapon types (pistol, rifle, sniper)
- [ ] Level progression and difficulty scaling
- [ ] Stealth takedown mechanics
- [ ] Gadgets (binoculars, hacking device)
- [ ] Alarms and reinforcement waves
- [ ] Multiple mission types
- [ ] Graphics improvements and animations
- [ ] Sound effects and music
- [ ] Leaderboard system

## Development

### Game Loop
The game runs at 60 FPS with a dedicated game thread. Each frame:
1. Updates game state (player position, enemy AI)
2. Checks collision and detection
3. Renders the scene
4. Processes input events

### Adding New Features

**Add a new enemy type:**
```kotlin
class SpecialEnemy : Enemy() {
    override fun patrol() {
        // Custom patrol logic
    }
}
```

**Add a new weapon:**
```kotlin
data class Weapon(val name: String, val damage: Int, val ammo: Int)
```

## License

MIT License

## Author

Shahil Gupta

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
