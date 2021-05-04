package com.mizuledevelopment.master.manager;

import com.mizuledevelopment.master.mongo.Mongo;
import com.mizuledevelopment.master.rcon.RconClient;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import org.bson.conversions.Bson;

import java.util.HashMap;
import java.util.UUID;

public class NodeManager {

    @Getter private static HashMap<String, ServerModel> serverCache;
    private String name;

    public NodeManager() {
        serverCache = new HashMap<>();
    }

    public static ServerModel getServer(String name) {
        ServerModel serverModel;

        if(!serverCache.containsKey(name)) {
            serverModel = Mongo.getServerCollection().find(Filters.eq("name", name)).first();
            if(serverModel == null) throw new NullPointerException("Cannot find server with name " + name);
            serverCache.put(serverModel.getName(), serverModel);
        }
        return serverCache.get(name);
    }

    public static ServerModel getServer(ServerModel serverModel) {

        if(!serverCache.containsKey(serverModel.getName())) {
            serverModel = Mongo.getServerCollection().find(Filters.eq("name", serverModel.getName())).first();
            if(serverModel == null) throw new NullPointerException("Cannot find punishment with name " + serverModel.getName());
            serverCache.put(serverModel.getName(), serverModel);
        }
        return serverCache.get(serverModel.getName());
    }

    public static void save(ServerModel serverModel) {
        if (serverModel.getName() == null) throw new NullPointerException("Name Cannot be equal to null!");

        Bson filter = Filters.eq("_id", serverModel.getUuid());

        serverCache.put(serverModel.getName(), serverModel);

        if (Mongo.getServerCollection().find(Filters.eq("name", serverModel.getName())).first() == null) {
            Mongo.getServerCollection().insertOne(serverModel);
            return;
        }
        Mongo.getServerCollection().replaceOne(filter, serverModel);
    }
}
