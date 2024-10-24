### Fixed:
- Fixed an issue where, even if the warp module was enabled, and no icon was present in a folder. It would try to create an icon to display data.
  - This is similar to the last issue, but in a different area related to retrieving data to display on the map. We now return an empty list if the config options are set to false.