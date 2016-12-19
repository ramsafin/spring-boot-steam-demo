package ru.kpfu.itis.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.kpfu.itis.model.entity.Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Daniel Shchepetov on 08.12.2016.
 */
public class GsonParser {

    private JsonParser parser;
    private List<Game> gameList;
    private List<Long> gameIdList;

    public List<Game> parseGameList(String stringToParse) {
        parser = new JsonParser();
        JsonElement jsonTree = parser.parse(stringToParse);
        JsonObject obj = jsonTree.getAsJsonObject();
        obj = obj.getAsJsonObject("applist");
        JsonElement apps = obj.get("apps");

        Iterator<JsonElement> iterator = apps.getAsJsonObject().get("app").getAsJsonArray().iterator();

        gameList = new ArrayList();
        while (iterator.hasNext()) {
            JsonElement element = iterator.next();
            Game game = new Game(element.getAsJsonObject().get("appid").getAsLong(), element.getAsJsonObject().get("name").getAsString());
            gameList.add(game);
        }
        return gameList;
    }

    public List<Long> parseUserGameList(String stringToParse) {
        parser = new JsonParser();
        JsonElement jsonTree = parser.parse(stringToParse);
        JsonObject obj = jsonTree.getAsJsonObject();
        obj = obj.getAsJsonObject("response");
        JsonElement games = obj.get("games");

        Iterator<JsonElement> iterator = games.getAsJsonArray().iterator();

        gameIdList = new ArrayList<Long>();
        while (iterator.hasNext()) {
            JsonElement element = iterator.next();

            gameIdList.add(element.getAsJsonObject().get("appid").getAsLong());
        }
        return gameIdList;
    }


    public List<String> parseUserInfoList(String stringToParse) {
        parser = new JsonParser();
        JsonElement jsonTree = parser.parse(stringToParse);
        JsonObject obj = jsonTree.getAsJsonObject();
        obj = obj.getAsJsonObject("response");
        JsonArray info = obj.getAsJsonArray("players");
        obj = info.get(0).getAsJsonObject();

        List<String> userInfo = new ArrayList<>();
        userInfo.add(obj.get("personaname").getAsString());
        userInfo.add(obj.get("avatar").getAsString());

        return userInfo;
    }
}
