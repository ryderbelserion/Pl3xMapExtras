# Pl3xMapExtras
An addon to [Pl3xMap](https://modrinth.com/plugin/pl3xmap)

### Quick Links
[Request Features](https://github.com/ryderbelserion/Pl3xMapExtras/issues)<br>
[Report Bugs](https://github.com/ryderbelserion/Pl3xMapExtras/issues)<br>

### Downloads
https://modrinth.com/project/pl3xmapextras

### Contact
[![Join us on Discord](https://discord.com/api/guilds/931330926653358090/widget.png?style=banner2)](https://discord.gg/w7yCw4M9za)

### Features
- Ability to show mobs/signs/banners on the webview
- Ability to show claims on the webview from plugins such as GriefPrevention or WorldGuard
    - ClaimChunk, GriefDefender, GriefPrevention, PlotSquared, WorldGuard
- Ability to show warps on the webview from plugins like EssentialsX or PlayerWarps

### Installation
1) Place [Pl3xMap](https://modrinth.com/plugin/pl3xmap) in your `plugins` folder
2) Place Pl3xMapExtras inside your server's `plugins` folder
3) Restart your server
4) Edit the config(s) to your liking
5) Restart server again

### Permissions
| Node                          | Description                                          | Default |
|-------------------------------|------------------------------------------------------|---------|
| `pl3xmapextras.banners.admin` | `allows the user to have banners show up on the map` | op      |
| `pl3xmapextras.signs.admin`   | `allows the user to have signs show up on the map`   | op      |
| `pl3xmapextras.reload`        | `the reload command`                                 | op      |
| `pl3xmapextras.help`          | `help command`                                       | op      |

### Building from source
Run the following command:
```
./gradlew assemble
```
The compiled jar will be in the `jars` folder

### Credit
Original Author: BillyGalbreath

Mob Icons: https://github.com/ADHDMC/Entity-Icons