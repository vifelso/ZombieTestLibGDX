package by.hryharenka.game.managers;

/**
 * Created by Andrei on 21.04.2017.
 */

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import by.hryharenka.game.pathfinding.GraphGenerator;
import by.hryharenka.game.pathfinding.GraphImp;


public class MapManager {
    public static int lvlTileWidth;
    public static int lvlTileHeight;
    public static int lvlPixelWidth;
    public static int lvlPixelHeight;
    public static int tilePixelWidth;
    public static int tilePixelHeight;
    public static TiledMap tiledMap;
    public static GraphImp graph;
    public static GraphImp graphTrees;
    public static Array<Vector2> trees;
    public static Array<Vector2> buildings;

    public static void loadLevel(TiledMap map) {
        tiledMap = map;
        // get level width/height in both tiles and pixels and hang on to the values
        MapProperties properties = map.getProperties();
        lvlTileWidth = properties.get("width", Integer.class);
        lvlTileHeight = properties.get("height", Integer.class);
        tilePixelWidth = properties.get("tilewidth", Integer.class);
        tilePixelHeight = properties.get("tileheight", Integer.class);
        lvlPixelWidth = lvlTileWidth * tilePixelWidth;
        lvlPixelHeight = lvlTileHeight * tilePixelHeight;


        //trees= GraphGenerator.getTrees(map);
        buildings = GraphGenerator.getBuildings(map);
        graph = GraphGenerator.generateGroundGraph(tiledMap);

    }

    public static void loadLevelTrees(TiledMap map) {
        tiledMap = map;
        // get level width/height in both tiles and pixels and hang on to the values
        MapProperties properties = map.getProperties();
        lvlTileWidth = properties.get("width", Integer.class);
        lvlTileHeight = properties.get("height", Integer.class);
        tilePixelWidth = properties.get("tilewidth", Integer.class);
        tilePixelHeight = properties.get("tileheight", Integer.class);
        lvlPixelWidth = lvlTileWidth * tilePixelWidth;
        lvlPixelHeight = lvlTileHeight * tilePixelHeight;


        trees = GraphGenerator.getTrees(map);
        buildings = GraphGenerator.getBuildings(map);
        graphTrees = GraphGenerator.generateGraphForTrees(tiledMap);

    }
}