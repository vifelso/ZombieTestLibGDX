package by.hryharenka.game.pathfinding;

import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

import by.hryharenka.game.managers.MapManager;
import by.hryharenka.game.sprites.Player;

/**
 * Created by Andrei on 21.04.2017.
 */

public class RunnerPathFinding {
    int lastEndTileX;
    int lastEndTileY;
    int startTileX;
    int startTileY;
    private IndexedAStarPathFinder<Node> pathFinder;
    private GraphPathImp resultPath = new GraphPathImp();

    public Array<Vector2> callPathFinding(Body body, int mouseX, int mouseY, Player player){
        if(player.isTree){
            pathFinder = new IndexedAStarPathFinder<Node>(MapManager.graphTrees,true);
        }

        else  pathFinder = new IndexedAStarPathFinder<Node>(MapManager.graph,true);

        startTileX = (int) body.getPosition().x;
        startTileY = (int) body.getPosition().y;

        lastEndTileX = ((int)mouseX/104)*104 +52;
        lastEndTileY = ((int)mouseY/104)*104 +52;
       /* System.out.println("mouse  x : " +  mouseX);
        System.out.println("mouse  y : " +  mouseY);
        System.out.println("Pathfinding end x : " +  lastEndTileX);
        System.out.println("Pathfinding end y : " +  lastEndTileY);
        System.out.println("Pathfinding start x : " +  startTileX);
        System.out.println("Pathfinding start y : " +  startTileY);*/
        Node startNode;
        Node endNode;
        if(player.isTree){
            startNode = MapManager.graphTrees.getNodeByXY(startTileX, startTileY);
            endNode = MapManager.graphTrees.getNodeByXY(lastEndTileX, lastEndTileY);

        }
        else {
            startNode = MapManager.graph.getNodeByXY(startTileX, startTileY);
            endNode = MapManager.graph.getNodeByXY(lastEndTileX, lastEndTileY);

        }

        pathFinder.searchNodePath(startNode,endNode, new HeuristicImp(), resultPath);

        return render(resultPath, body);

    }

    public  Array<Vector2> render(GraphPathImp path, Body body){
        Array<Vector2> cp = new Array<>();
            Iterator<Node> pathIterator = path.iterator();
            Node priorNode = null;
            while (pathIterator.hasNext()) {
                Node node = pathIterator.next();
                if (priorNode != null) {
                    int index2 = priorNode.getIndex();
                    cp.add(new Vector2(MapManager.tilePixelWidth * (index2 % MapManager.lvlTileWidth)+52, MapManager.tilePixelHeight * (index2 / MapManager.lvlTileHeight)+52));

                }
                priorNode = node;
            }
        cp.add(new Vector2(lastEndTileX,lastEndTileY));
        return cp;

    }

}
