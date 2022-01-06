package me.secretagent.machine;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import me.secretagent.machine.commands.CommandManager;
import me.secretagent.machine.commands.impl.BuyCommand;
import me.secretagent.machine.commands.impl.ItemCommand;
import me.secretagent.machine.commands.impl.ItemsCommand;
import me.secretagent.machine.items.Item;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import org.bson.Document;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Machine {

    private static final Machine machine = new Machine();

    public static Machine getMachine() {
        return machine;
    }

    public static void main(String args[]) throws Exception {
        getMachine().start();
    }

    public JDA jda;
    public MongoClient client;
    public MongoDatabase database;
    public List<Item> itemList = new ArrayList<>();

    public void start() throws LoginException, InterruptedException {
        MongoClientURI uri = new MongoClientURI("mongodb+srv://bridgescrims:bridgescrims@experiment.tdv3u.mongodb.net/test");
        client = new MongoClient(uri);
        database = client.getDatabase("bridgescrims");
        MongoCursor cursor = database.getCollection("vending").find().iterator();
        while (cursor.hasNext()) {
            Document document = (Document) cursor.next();
            DBObject object = new BasicDBObject(document);
            Item item = new Item(object);
            itemList.add(item);
        }
        jda = JDABuilder.createDefault("ODQ1MzQxMzg1NzA0MjEwNTE0.YKfjhA.__W933LQrUV3Qzxl80Y2S9I3Dc0")
                .setActivity(Activity.playing("Bridge"))
                .build();
        jda.awaitReady();
        CommandManager manager = new CommandManager(jda);
        manager.registerCommand(new BuyCommand());
        manager.registerCommand(new ItemCommand());
        manager.registerCommand(new ItemsCommand());
        System.out.println("Registered Commands!");
    }

    public Item fromName(String name) {
        for (Item item : itemList) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

}
