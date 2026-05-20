## Bugs Fixed 🐛
- Fixed an issue with incorrect logging placeholders
- Fixed an issue with the names of banners not showing up in the tooltip.
  - Adding `<name>` to the tooltip configs fixed this.
- Fixed an issue with `storage/banners.json` and `banners/config.yml` not extracting
  - This fixes another issue where the feature is not enabled because it defaults to false.
- Fixed an issue with banner permissions due to a missing `.`
- Fixed an issue with Fusion API ([#911](https://github.com/Crazy-Crew/CrazyCrates/issues/911)) where spaces were not acceptable in the path.