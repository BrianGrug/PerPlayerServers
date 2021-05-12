# PPS (Per Player Servers)

Create servers on the fly with Docker.

## Installation

```bash
git clone https://github.com/DaddyImPregnant/PerPlayerServers.git
mvn clean package
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

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update testsuse Lombok as appropriate.

## License
[MIT](https://github.com/DaddyImPregnant/PerPlayerServers/blob/master/LICENSE)
