package by.hryharenka.game.pathfinding;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


import org.omg.CORBA.INTERNAL;

import by.hryharenka.game.managers.MapManager;

/**
 * Created by Andrei on 21.04.2017.
 */




public class GraphGenerator {
    public static GraphImp generateGroundGraph(TiledMap map) {
        Array<Node> nodes = new Array<Node>();

        TiledMapTileLayer tiles = (TiledMapTileLayer)map.getLayers().get(0);
        TiledMapTileLayer tilesLayerObjects = (TiledMapTileLayer)map.getLayers().get(1);
        //TiledMapTileLayer tilesLayerTrees= (TiledMapTileLayer)map.getLayers().get(3);
        int mapHeight = MapManager.lvlTileHeight;
        int mapWidth = MapManager.lvlTileWidth;

        // Loops over the tiles in the map, starting from bottom left corner
        // iterating left to right, then down to up
        for (int y = 0; y < mapHeight; ++y) {
            for (int x = 0; x < mapWidth; ++x) {
                // generate a node for each tile so that they all exist when we create connections
                Node node = new Node();
                node.type = Node.Type.REGULAR;
                nodes.add(node);
            }
        }

        for (int y = 0; y < mapHeight; ++y) {
            for (int x = 0; x < mapWidth; ++x) {
                //main
                TiledMapTileLayer.Cell target = tiles.getCell(x, y);
                TiledMapTileLayer.Cell up = tiles.getCell(x, y+1);
                TiledMapTileLayer.Cell left = tiles.getCell(x-1, y);
                TiledMapTileLayer.Cell right = tiles.getCell(x+1, y);
                TiledMapTileLayer.Cell down = tiles.getCell(x, y-1);

                //objects
                TiledMapTileLayer.Cell targetObject = tilesLayerObjects.getCell(x, y);
                TiledMapTileLayer.Cell upObject = tilesLayerObjects.getCell(x, y+1);
                TiledMapTileLayer.Cell leftObject = tilesLayerObjects.getCell(x-1, y);
                TiledMapTileLayer.Cell rightObject = tilesLayerObjects.getCell(x+1, y);
                TiledMapTileLayer.Cell downObject = tilesLayerObjects.getCell(x, y-1);



                Node targetNode = nodes.get(mapWidth * y + x);
                if (target != null && targetObject == null  ) {
                    if (y != 0 && down != null && downObject == null ) {
                        Node downNode = nodes.get(mapWidth * (y - 1) + x);
                        targetNode.createConnection(downNode, 1);
                    }
                    if (x != 0 && left != null && leftObject == null ) {
                        Node leftNode = nodes.get(mapWidth * y + x - 1);
                        targetNode.createConnection(leftNode, 1);
                    }
                    if (x != mapWidth - 1 && right != null && rightObject == null ) {
                        Node rightNode = nodes.get(mapWidth * y + x + 1);
                        targetNode.createConnection(rightNode, 1);
                    }
                    if (y != mapHeight - 1 && up != null && upObject == null ) {
                        Node upNode = nodes.get(mapWidth * (y + 1) + x);
                        targetNode.createConnection(upNode, 1);
                    }
                }
            }
        }

        return new GraphImp(nodes);
    }







    public static GraphImp generateGraphForTrees(TiledMap map) {
        Array<Node> nodes = new Array<Node>();

        TiledMapTileLayer tiles = (TiledMapTileLayer)map.getLayers().get(0);
        TiledMapTileLayer tilesLayerObjects = (TiledMapTileLayer)map.getLayers().get(1);
        //TiledMapTileLayer tilesLayerTrees= (TiledMapTileLayer)map.getLayers().get(3);
        int mapHeight = MapManager.lvlTileHeight;
        int mapWidth = MapManager.lvlTileWidth;

        // Loops over the tiles in the map, starting from bottom left corner
        // iterating left to right, then down to up
        for (int y = 0; y < mapHeight; ++y) {
            for (int x = 0; x < mapWidth; ++x) {
                // generate a node for each tile so that they all exist when we create connections
                Node node = new Node();
                node.type = Node.Type.REGULAR;
                nodes.add(node);
            }
        }

        for (int y = 0; y < mapHeight; ++y) {
            for (int x = 0; x < mapWidth; ++x) {
                //main
                TiledMapTileLayer.Cell target = tiles.getCell(x, y);
                TiledMapTileLayer.Cell up = tiles.getCell(x, y+1);
                TiledMapTileLayer.Cell left = tiles.getCell(x-1, y);
                TiledMapTileLayer.Cell right = tiles.getCell(x+1, y);
                TiledMapTileLayer.Cell down = tiles.getCell(x, y-1);

                //objects
                TiledMapTileLayer.Cell targetObject = tilesLayerObjects.getCell(x, y);
                TiledMapTileLayer.Cell upObject = tilesLayerObjects.getCell(x, y+1);
                TiledMapTileLayer.Cell leftObject = tilesLayerObjects.getCell(x-1, y);
                TiledMapTileLayer.Cell rightObject = tilesLayerObjects.getCell(x+1, y);
                TiledMapTileLayer.Cell downObject = tilesLayerObjects.getCell(x, y-1);



                Node targetNode = nodes.get(mapWidth * y + x);
                if (target != null && targetObject == null  ) {
                    if (y != 0 && down != null && downObject == null ) {
                        Node downNode = nodes.get(mapWidth * (y - 1) + x);
                        targetNode.createConnection(downNode, 1);
                    }
                    if (x != 0 && left != null && leftObject == null ) {
                        Node leftNode = nodes.get(mapWidth * y + x - 1);
                        targetNode.createConnection(leftNode, 1);
                    }
                    if (x != mapWidth - 1 && right != null && rightObject == null ) {
                        Node rightNode = nodes.get(mapWidth * y + x + 1);
                        targetNode.createConnection(rightNode, 1);
                    }
                    if (y != mapHeight - 1 && up != null && upObject == null ) {
                        Node upNode = nodes.get(mapWidth * (y + 1) + x);
                        targetNode.createConnection(upNode, 1);
                    }
                }
            }
        }

        return new GraphImp(nodes);
    }

    public static Array<Integer> getConverter(Array<Vector2> coordinates){
        int pointX;
        int pointY;
        int calculatedCell = 0;
        Array<Integer> array= new Array<>();
        for(int i =0;i < coordinates.size; i++){
            pointX = (int) coordinates.get(i).x -52;
            pointY = (int) coordinates.get(i).y -52;
            calculatedCell = (int)((pointY/104)* 10)+ (int)pointX/104;
            array.add(calculatedCell);

        }


        return array;
    }


    public static Array<Vector2> getTrees(TiledMap map){
        Array<Vector2> coordinatesTrees = new Array<>();
        Vector2 vector2;
        MapObjects mapObjects= map.getLayers().get("trees").getObjects();
        for(MapObject mapObject:mapObjects){
            float x = (Float) mapObject.getProperties().get("x") +52;
            float y = (Float) mapObject.getProperties().get("y") +52;
            vector2 = new Vector2(x,y);
            coordinatesTrees.add(vector2);
        }

        return coordinatesTrees;
    }


    public static Array<Vector2> getBuildings(TiledMap map){
        Array<Vector2> coordinatesBuildings = new Array<>();
        Vector2 vector2;
        MapObjects mapObjects= map.getLayers().get("buildings").getObjects();
        for(MapObject mapObject:mapObjects){
            float x = (Float) mapObject.getProperties().get("x") +52;
            float y = (Float) mapObject.getProperties().get("y") +52;
            vector2 = new Vector2(x,y);
            coordinatesBuildings.add(vector2);
        }

        return coordinatesBuildings;
    }


}