package com.mizuledevelopment.master.mongo;

import com.mizuledevelopment.master.objects.ServerModel;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Getter
public class MongoManager {

    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;

    private final MongoCollection<ServerModel> serverCollection;

    public MongoManager(String host, int port) {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientSettings settings = MongoClientSettings.builder().codecRegistry(pojoCodecRegistry).applyConnectionString(new ConnectionString("mongodb://" + host + ":" + port)).build();
        this.mongoClient = MongoClients.create(settings);
        this.mongoDatabase = mongoClient.getDatabase("PerPlayerServers");

        this.serverCollection = this.mongoDatabase.getCollection("servers", ServerModel.class);
    }

}
