package com.mizuledevelopment.master.manager;

import com.mizuledevelopment.master.MasterApplication;
import com.mizuledevelopment.shared.objects.ServerModel;
import com.mongodb.client.model.Filters;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class NodeManager {

    private final Map<String, ServerModel> serverCache = new HashMap<>();
    private final Map<String, ServerModel> activeServers = new HashMap<>();

    public ServerModel getServer(ServerModel serverModel) {
        return this.getServer(serverModel.getName());
    }

    public ServerModel getServer(String serverName) {
        if (!this.serverCache.containsKey(serverName)) {
            ServerModel serverModel = MasterApplication.getInstance().getMongoManager().getServerCollection().find(Filters.eq("name", serverName)).first();

            if (serverModel == null)
                throw new NullPointerException("Cannot find server with name " + serverName);

            this.serverCache.put(serverModel.getName(), serverModel);
        }

        return this.serverCache.get(serverName);
    }

    public void cache(ServerModel serverModel) {
        this.activeServers.put(serverModel.getName(), serverModel);
    }

    public void removeServer(ServerModel serverModel) {
        this.removeServer(serverModel.getName());
    }

    public void removeServer(String serverName) {
        this.activeServers.remove(serverName);
        MasterApplication.getInstance().getMongoManager().getServerCollection().deleteOne(Filters.eq("name", serverName));
    }

    public void save(ServerModel serverModel) {
        if (serverModel.getName() == null)
            throw new NullPointerException("Name Cannot be equal to null!");

        this.serverCache.put(serverModel.getName(), serverModel);

        if (MasterApplication.getInstance().getMongoManager().getServerCollection().find(Filters.eq("name", serverModel.getName())).first() == null)
            MasterApplication.getInstance().getMongoManager().getServerCollection().insertOne(serverModel);
        else
            MasterApplication.getInstance().getMongoManager().getServerCollection().replaceOne(Filters.eq("name", serverModel.getName()), serverModel);
    }

}
