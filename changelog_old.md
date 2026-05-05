## 0.13.0-rc

* Added a full in-game configuration GUI (no dependencies required)
* Introduced a festival system (`eclipticseasons:special_days`)
* Added a seasonal background music system
* Expanded the calendar system with sub-seasons, months, and day tracking
* Added seasonal color support for single-tint block models
* New season definitions datapack `Spring Grass`
* Removed biome-based local weather; replaced with dimension-level weather (API remains backward-compatible)
* Reworked the weather system to be fully data-driven via datapacks
  * Seasonal variation in rain (frequency, duration, intensity)
  * Fixed incorrect thunder triggering
* Improved snow map color handling
* Fixed ambient sound loading (now supports vanilla `sounds.json`) and add field `ignored_biomes`
* Fixed snow cover issues on certain blocks (e.g., glass panes)
* Added debug tools and keybind support
* General performance and compatibility optimizations (e.g., Platform class loading)
* Moved Voxy support (1.20.1 / 1.21.1) to a separate compatibility mod
* Relaxed version requirements for Distant Horizons