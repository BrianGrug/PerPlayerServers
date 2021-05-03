package com.mizuledevelopment.master.mongo;

import com.mizuledevelopment.master.manager.ServerModel;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Mongo {

    @Getter private static MongoClient mongoClient;
    @Getter private static MongoDatabase mongoDatabase;
    @Getter private static MongoCollection<ServerModel> serverCollection;

    public void connectMongo() {
        List<MongoCredential> creds = new ArrayList<>();
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        String connection;
        connection = "mongodb://" + "localhost" + ":" + "27017";

        MongoClientSettings settings = MongoClientSettings.builder().codecRegistry(pojoCodecRegistry).applyConnectionString(new ConnectionString(connection)).build();
        mongoClient = MongoClients.create(settings);

        mongoDatabase = mongoClient.getDatabase("PerPlayerServers");

        serverCollection = mongoDatabase.getCollection("servers", ServerModel.class);

    }
}
