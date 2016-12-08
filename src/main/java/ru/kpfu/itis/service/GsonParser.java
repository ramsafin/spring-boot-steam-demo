package ru.kpfu.itis.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.kpfu.itis.model.entity.Game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Daniel Shchepetov on 08.12.2016.
 */
public class GsonParser {
  private JsonParser parser = new JsonParser();

    public String parseGameList(String stringToParse){
        JsonElement jsonTree = parser.parse(stringToParse);
        JsonObject obj = jsonTree.getAsJsonObject();
        obj = obj.getAsJsonObject("app_list");
        JsonElement apps = obj.get("apps");

        Iterator<JsonElement> iterator = apps.getAsJsonObject().get("app").getAsJsonArray().iterator();
        Game game = new Game();
       // game.setId(apps.getAsJsonObject().get("app").getAsJsonArray().get(1).getAsJsonObject().get("appid").getAsLong());
        while (iterator.hasNext()){
            JsonElement element = iterator.next();
            System.out.println(element.getAsString());
        }

        return apps.getAsString();
    }


}
