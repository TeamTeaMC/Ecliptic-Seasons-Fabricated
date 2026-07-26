- Used Fabric Attachment system instead of CardinalComponent to store Biome Holder Chunk info.
- Fixed an issue that assumes Frozen River as Warm Region.
- Updated river biome cache generation to use climate-based evaluation, improving winter snow behavior.
- Fixed a potential server-side memory leak caused by improperly retained chunk cache data.

#### Migration Notice

For players upgrading from earlier versions, old `surface_biome_cache` data may still affect existing chunks.
We recommend : Disable:`Weather -> IndependentRiverWeather`
(in the `Resource` category in the configuration file)


