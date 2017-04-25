package by.hryharenka.game.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;

import by.hryharenka.game.managers.MapManager;


/**
 * Created by Andrei on 21.04.2017.
 */

public class HeuristicImp implements Heuristic<Node> {
    @Override
    public float estimate(Node startNode, Node endNode) {
        int startIndex = startNode.getIndex();
        int endIndex = endNode.getIndex();

        int startY = startIndex / MapManager.lvlTileWidth;
        int startX = startIndex % MapManager.lvlTileWidth;

        int endY = endIndex / MapManager.lvlTileWidth;
        int endX = endIndex % MapManager.lvlTileWidth;

        // magnitude of differences on both axes is Manhattan distance (not ideal)
        float distance = Math.abs(startX - endX) + Math.abs(startY - endY);

        return distance;
    }
}