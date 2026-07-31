### 0.14.3

- Optimized surface biome cache recalculation for existing worlds after installing Ecliptic Seasons or clearing cached
  data.

### 0.14.2

- Clean client level cache when solar term changes

### 0.14.1

- Used Fabric Attachment system instead of CardinalComponent to store Biome Holder Chunk info.
- Fixed an issue that assumes Frozen River as Warm Region.
- Fixed a potential server-side memory leak caused by improperly retained chunk cache data.

### 0.14.0

- Updated river biome cache generation to use climate-based evaluation, improving winter snow behavior.

### 0.13.10

- Improved crop growth information under poor network conditions. If a crop is affected by the seasonal growth system,
  its name is now displayed immediately while detailed information is being retrieved from the server.
- Removed the unused `BIOME_WEATHER_QUERY_LIST` cache from `WeatherManager`. This legacy cache was no longer used and
  could increase the risk of memory leaks in complex modded environments due to unexpected `ClientLevel` lifecycles.

### 0.13.9

- Updated biome tags: added `c:is_aquatic_icy` to `extreme_cold`.
- Updated rain/rainless resource pack biome rules to use `minecraft:river` instead of `#c:is_river`.

### 0.13.8

- Seasonal particles are now generated based on seasonal signals instead of the natural season.
- Fixed compatibility issues between extra snow layers and snow cover removal.

### 0.13.7

- Added the Season Sensor block.
- Improved butterfly particle movement and rendering behavior during spring.
- Added German translations. Thanks to M4ximum93 for the contribution.
- Fixed #158: Item information rendered on Create: Item Drawer surfaces may display incorrectly when Extra Snow Layers
  are enabled. (1.21+ only, requires Sodium and specific mod combinations)
- Updated several Patchouli book entries to better reflect current mechanics and gameplay behavior.
- Various internal optimizations and compatibility improvements.

### 0.13.6

- (Neoforge/Forge) Simplified Seasonal Prayer Ritual progression by removing random progress triggers.
- Added configurable bone meal restrictions under unsuitable growing conditions (`RestrictBoneMealBySeason`). Enabled by
  default through the built-in datapack.
- Rebalanced rainfall and thunderstorm probabilities.
- Added crop growth diagnostics for Jade and TOP, showing growth chances and environmental conditions when crops are
  unable to grow normally.

### 0.13.5

- Optimized the Growth Detector's detection logic to correctly recognize Greenhouse Cores and humidity changes.

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