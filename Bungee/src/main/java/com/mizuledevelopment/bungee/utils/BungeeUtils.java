package com.mizuledevelopment.bungee.utils;

import com.mizuledevelopment.bungee.BungeePlugin;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

import java.net.InetSocketAddress;

public class BungeeUtils {

    public static void addServer(String name, InetSocketAddress address, boolean restricted) {
        ProxyServer.getInstance().getServers().put(name, ProxyServer.getInstance().constructServerInfo(name, address, "Docker created server", restricted));
    }

    public static void removeServer(String name) {
        BungeePlugin.getInstance().getProxy().getPlayers().forEach(player -> player.disconnect(new TextComponent("This server has been closed.")));
        ProxyServer.getInstance().getServers().remove(name);
    }

}
