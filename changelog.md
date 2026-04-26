* The toggle for banners is no longer in `config.yml`, it's moved to banners/`config.yml`
    * It's disabled by default, set it to `true` in banners/`config.yml`, then restart your server.
* The existing banners directory should be deleted, as the layout changed.
* `/pl3xmapextras help` is removed, as only `pl3xmapextras reload` is needed.
    * You should re-generate the messages.yml.
* ClaimChunk support was removed, The developer no longer updates it.
* All banner data is stored in storage/banners.json.
* Fixed an issue with icons throwing an `IllegalStateException` by switching to Optionals
  * If no icon is found, A simple message will log to console instead.

**New Permissions:**
- pl3xmapextras.banners.admin (default to op)
- pl3xmapextras.banners.place (default to op)
- pl3xmapextras.banners.remove (default to op)
- pl3xmapextras.access (gives access to /pl3xmapextras)