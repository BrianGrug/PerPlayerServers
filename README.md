
# PerPlayerServers

PerPlayerServers (PPS for short) is a Java program that works with Docker to deploy Minecraft servers on the fly!

## Requirements

* Java 11+
* Maven
* Docker
* Redis
## Deployment

For all of the following examples, I will be using the name HelloWorld and the image daddyimpregnant/spigot:latest.

Creating a server
```bash
create HelloWorld spigot
```

Connecting to servers is easy!
```bash
connect HelloWorld
```

Stopping a server
```bash
stop HelloWorld
```

Removing a server is easy too!
```bash
remove HelloWorld
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to use Lombok as appropriate and follow proper OOP practices!

## License

[MIT](https://github.com/DaddyImPregnant/PerPlayerServers/blob/master/LICENSE)