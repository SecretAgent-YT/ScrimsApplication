package me.secretagent.machine.items;

import com.mongodb.DBObject;

public class Item {

    private final int id;
    private final String name;
    private final int price;
    private final String image;

    public Item(DBObject object) {
        this.id = (int) object.get("_id");
        this.name = object.get("name").toString();
        this.price = (int) object.get("price");
        this.image = object.get("image").toString().replace("www.google.com/url?sa=i&url=https%3A%2F%2F", "");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

}
