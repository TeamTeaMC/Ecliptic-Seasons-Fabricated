### 0.13.3.3

- Added an option to disable rain and snow particle texture adjustments.
- Added support for commands such as `/ecliptic solar setTerm beginning_of_summer true`, allowing the target solar
  term/season to be set to the next nearest occurrence instead of always using the first year.

### 0.13.3.2

- Fixed several advancement display issues.
- Improved compatibility when detecting built-in advancements.

### 0.13.3.1

- `BeforeCheckSnowStatusEvent` is no longer fired in order to improve weather simulation performance.

## 0.13.3

- Enabled the Not Ignore River datapack by default and fixed its incorrect location.
- Improved chunk loading performance by disabling river weather merging during initial chunk loading.
- River biomes are now treated as non-rainy while `IndependentRiverWeather` is enabled.
- Existing worlds may optionally run `/ecliptic debug reset surface_biome_cache` to reset the biome weather cache.
- To restore the old river weather behavior, disable `IndependentRiverWeather` in the config.

## 0.13.2

- Adjusted rainfall frequency from late spring to early summer.

## 0.13.1

- Added an in-world UI for the Growth Detector to display information.

## 0.13.0-rc-2

* Various additional optimizations and improvements.

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