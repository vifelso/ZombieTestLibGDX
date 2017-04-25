package by.hryharenka.game.pathfinding;


import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;

import by.hryharenka.game.managers.MapManager;


/**
 * Created by Andrei on 21.04.2017.
 */

public class GraphImp implements IndexedGraph<Node> {
    protected Array<Node> nodes = new Array<>();
    protected int capacity;

    public GraphImp() {
        super();
    }

    public GraphImp(int capacity) {
        this.capacity = capacity;
    }


    public GraphImp(Array<Node> nodes) {
        this.nodes = nodes;

        // speedier than indexOf()
        for (int x = 0; x < nodes.size; ++x) {
            nodes.get(x).index = x;
        }
    }

    public Node getNodeByXY(int x, int y) {
        int modX = x / MapManager.tilePixelWidth;
        int modY = y / MapManager.tilePixelHeight;

        return nodes.get(MapManager.lvlTileWidth * modY + modX);
    }

    @Override
    public int getIndex(Node node) {
        return nodes.indexOf(node, true);
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        return fromNode.getConnections();
    }
}