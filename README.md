# DeathGhostFix

DeathGhostFix reduces same-tick death ghosting by delaying final death decisions until the end of the server tick. Without DeathGhostFix, lethal damage can be finalized before same-tick survival actions like Instant Health splash potions or last-moment totem swaps are processed.

## Installation

Install on the server. A client-only install on multiplayer servers will not affect server-authoritative damage, healing, or totem behavior.

## Versions

- `1.21.11`: Minecraft Java 1.21.11
- `26.1.x`: Minecraft Java 26.1 through 26.1.2

## License

Unlicense
