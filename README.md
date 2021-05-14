![](banner.jpg)
[![](https://img.shields.io/github/workflow/status/DaddyImPregnant/PerPlayerServers/Windows%20-%20Java%20-%20Maven?label=Windows%20Build&logo=windows&logoColor=%23FFFFFF&style=flat-square)](https://github.com/DaddyImPregnant/PerPlayerServers/actions/workflows/windows-java-maven.yml)
[![](https://img.shields.io/github/workflow/status/DaddyImPregnant/PerPlayerServers/Linux%20-%20Java%20-%20Maven?label=Linux%20Build&logo=linux&logoColor=%23FFFFFF&style=flat-square)](https://github.com/DaddyImPregnant/PerPlayerServers/actions/workflows/linux-java-maven.yml)
[![](https://img.shields.io/github/workflow/status/DaddyImPregnant/PerPlayerServers/macOS%20-%20Java%20-%20Maven?label=macOS%20Build&logo=apple&logoColor=%23FFFFFF&style=flat-square)](https://github.com/DaddyImPregnant/PerPlayerServers/actions/workflows/macos-java-maven.yml)

# PerPlayerServers
Simplify server creation with Docker and make it possible to create servers on the fly for things like PvP battles, events, and even per-match servers.

## Overview
This is a project being developed by the community, and is not affiliated with any of the companies belonging to [Mojang](https://mojang.com), [Microsoft](https://www.microsoft.com) and [Docker](https://www.docker.com). The project is developed with Java version 8 making it possible to run it on most servers today, and with the build automation tool called Maven.

## How to build
```bash
git clone https://github.com/DaddyImPregnant/PerPlayerServers.git
cd PerPlayerServers
mvn
```

## Usage
For all of the following examples, I will be using the name HelloWorld and the image daddyimpregnant/spigot:latest.

Creating a server
```
create HelloWorld spigot
```
Connecting to servers is easy!
```
connect HelloWorld
```
Removing a server is easy too!
```
stop HelloWorld
remove HelloWorld
```

## Contributions
This project will always remain open source and any kind of contribution is welcome. By participating in this project, you agree to keep common sense and contribute in a positive way. For major changes, please open an issue first to discuss what you would like to change.

## License
[MIT](https://github.com/DaddyImPregnant/PerPlayerServers/blob/master/LICENSE)