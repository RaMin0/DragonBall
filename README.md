# DragonBall

A Java-based DragonBall game featuring turn-based battles, character progression, and an explorable world map.

## Demo

[![DragonBall Game Demo](https://i.ytimg.com/vi/WtfG1aSWisc/hqdefault.jpg)](https://www.youtube.com/watch?v=WtfG1aSWisc)

## Features

- Multiple playable fighter races (Earthling, Saiyan, Namekian, Frieza, Majin)
- Turn-based battle system with physical, super, and ultimate attacks
- RPG elements including XP, leveling, and ability points
- Collectable items (Senzu Beans, Dragon Balls)
- World exploration with random encounters
- Both GUI and Console interfaces

## Game Mechanics

### Fighter Races

- **Earthling**: Balanced fighter with Ki regeneration
- **Saiyan**: High damage dealer with transformation ability
- **Namekian**: Defensive fighter with health regeneration
- **Frieza**: High blast damage specialist
- **Majin**: Tank with high health points

### Battle System

- Physical attacks generate Ki
- Super attacks require Ki to use
- Ultimate attacks require high Ki levels
- Block action consumes stamina to reduce damage
- Fighters regenerate stamina each turn

### Character Progression

- Gain XP from winning battles
- Level up to earn ability points
- Upgrade attributes:
  - Health Points
  - Blast Damage
  - Physical Damage
  - Ki
  - Stamina

## Building and Running

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Make (for building)

### Build Instructions

```bash
make compile
```

### Running the Game

GUI Mode:
```bash
make
```

Console Mode:
```bash
make console
```

## Project Structure

- `src/` - Source code
  - `controller/` - Game controllers (GUI & Console)
  - `model/` - Game logic and data structures
  - `view/` - GUI components
  - `exceptions/` - Custom exception classes
- `gfx/` - Game graphics and sprites
- `sfx/` - Sound effects
- `res/` - Game resources (fonts)
- `Database-*.csv` - Game data files

## Controls

### GUI Mode

- Arrow keys for movement
- ESC to open menu
- Enter/Space to advance text

### Console Mode

- Follow on-screen prompts
- Enter commands when requested

## Game Data

The game uses CSV files to store:
- Available attacks (`Database-Attacks.csv`)
- Dragon wishes (`Database-Dragons.csv`)
- Enemy fighters (`Database-Foes-Range*.csv`)
