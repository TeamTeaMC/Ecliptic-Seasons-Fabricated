# [Ecliptic Seasons: Fabricated](https://legacy.curseforge.com/minecraft/mc-mods/ecliptic-seasons)

![Ecliptic Seasons](https://github.com/user-attachments/assets/549d6626-d78e-4901-8b96-f420a6c2d3ea)

“节气”是Minecraft的一个中国风模组，关于四季。

Ecliptic Seasons is a Chinese mod about seasons and weather.

### LICENSE

* For code: BSD-3
* For resources: CC BY-NC-SA 4.0
* Please do not port arbitrarily, communication can make the community better, and please respect our work.
* For disc *Snowless Homeland*: authorized by Beishanwei & Orangesoda for distribution and instrumental adaptation.

## 1. What is the "Fabricated" Version?

Ecliptic Seasons: Fabricated is not a mere platform port; it is a complete, ground-up rewrite built directly on the
Fabric API.

To achieve peak performance and rendering compatibility, we adopted a "back-to-basics" development philosophy: rejecting
bloated cross-platform assembly frameworks and stripping away heavy functional logic. It is a "High-Performance Engine
Edition" specifically tailored for players who demand extreme fluid-smoothness, massive render distances (such as Voxy),
and a pure, non-intrusive seasonal atmosphere.

## 2. Key Differences from the Standard (NeoForge) Version

While the NeoForge version offers an expansive suite of features—Fabricated focuses exclusively on the core of
environmental simulation:

🚀 Lite-Registry Footprint: Removed Chunk Snow Attachment, Season Cores, Copper Grates, and all decorative items. By
eliminating unnecessary BlockEntities, Synced connect, CPU and memory overhead are reduced to the absolute minimum.

🛠️ Native Reconstruction: Completely bypasses cross-platform abstraction layers to interface directly with native Fabric
hooks.

💎 Built for Voxy: Deeply optimized environmental parameter pipelines ensure seamless integration with LOD rendering
engines like Voxy, providing buttery-smooth seasonal transitions even at extreme render distances.

🍃 Algorithm-Driven: Retains the core 24 Solar Terms system, dynamically influencing day/night length, snowy world,
seasonal model, and global precipitation patterns.

![A Show](https://github.com/user-attachments/assets/e0d3c694-128c-427f-8d15-34910694f866)

## 3. Core Features

24 Solar Terms System: Implements the traditional Chinese calendar, dividing the year into 24 distinct periods, each
with unique climatic characteristics.

Dynamic Day/Night Cycles: Experience shifting day lengths—prolonged nights in winter and extended days in summer.

Optimized Precipitation: Localized rainfall probabilities based on biomes and solar terms. No more endless overcast
skies in arid regions.

Atmospheric Rendering: Supports dynamic Biome Colors, providing precise environmental data to drive Shaders and other
visual enhancers.

Maximum Compatibility: Does not force changes to biome block states, ensuring out-of-the-box compatibility with nearly
all terrain-generation mods.

## 4.Quick support for Ecliptic Seasons data packs.

For **overworld agro biomes**, the following special tags are provided:

* `eclipticseasons:agro/warm` – applied to **warm regions**
* `eclipticseasons:agro/cold` – applied to **cold regions**
* `eclipticseasons:agro/hot` – applied to **hot regions**

For **biome rain** types, check the existing tags in the data folder.

* `eclipticseasons:rain/seasonal`,`eclipticseasons:rain/seasonal/hot`,`eclipticseasons:rain/seasonal/cold` indicates
  that the biome has distinct seasonal changes.
* `eclipticseasons:rain/monsoonal` indicates that the biome has seasonal wet and dry periods.
* The `eclipticseasons:is_small` tag is a special tag for marking small biomes, and generally doesn’t need to be used.
* The remaining tags, `eclipticseasons:rain/rainless`, `eclipticseasons:rain/arid`, `eclipticseasons:rain/droughty`,
  `eclipticseasons:rain/soft`, `eclipticseasons:rain/rainy`, indicate biomes with only slight seasonal changes in
  rainfall, with only differences in precipitation amounts.

For **biome color** types, it's similar. And it's actually recommended to use resource packs to achieve more customized
colors.

* `eclipticseasons:color/seasonal`, `eclipticseasons:color/seasonal/hot`, `eclipticseasons:color/seasonal/cold` —
  represent seasonal colors for normal, hot, and cold biomes respectively.
* `eclipticseasons:color/monsoonal` — represents colors for (tropical) monsoonal climate biomes.
* `eclipticseasons:color/stable`, `eclipticseasons:color/slightly` — represent areas with stable and slight color
  changes.

For crops, it’s more complex. In addition to using various tags, you can assign categories to tag item or block if they
haven't a bind item.
A crop can only have one season growth requirement tag and one humidity growth requirement tag. When grown in the wrong
environment, its growth rate will slow significantly, and vice versa.

* For seasons, there are 15 preset types to choose from based on your needs, each word indicates a suitable season:
  `eclipticseasons:crops/spring`, `eclipticseasons:crops/summer`, `eclipticseasons:crops/autumn`,
  `eclipticseasons:crops/winter`, `eclipticseasons:crops/spring_summer`, `eclipticseasons:crops/spring_autumn`,
  `eclipticseasons:crops/spring_winter`, `eclipticseasons:crops/summer_autumn`, `eclipticseasons:crops/summer_winter`,
  `eclipticseasons:crops/autumn_winter`, `eclipticseasons:crops/spring_summer_autumn`,
  `eclipticseasons:crops/spring_summer_winter`, `eclipticseasons:crops/spring_autumn_winter`,
  `eclipticseasons:crops/summer_autumn_winter`, `eclipticseasons:crops/all_seasons`
* For humidity, there are 15 preset types as well, the two words mean the lowest and the highest suitable humidity: `
  eclipticseasons:crops/arid_ari