package by.hryharenka.game.tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;


import by.hryharenka.game.sprites.Player;


/**
 * Created by Andrei on 22.04.2017.
 */

public class DirectionAnVelocitySetter {
    public enum Direction {
        LEFT,
        TOP,
        RIGHT,
        DOWN
    }

    float previousDelta = 0;
    int index;
    float t;
    Direction d;
    Direction previousDerection;
    Vector2 currentPoint;
    public Body body;
    public Array<Vector2> points;
    float delta;
    float currentPointX;
    float currentPointY;

    public DirectionAnVelocitySetter(Body body, Array<Vector2> points, float delta, int index) {
        this.index = index;
        this.points = points;
        currentPointX = body.getPosition().x;
        currentPointY = body.getPosition().y;
        currentPoint = new Vector2();
        this.delta = delta;

    }

    public Direction setDirectionAndVelocity(int index, Body body, float s, Player player, float deltaTime) {
        this.body = body;
        boolean isChanged = false;
        t = 0;
        if (previousDerection != d) {

        }
        previousDerection = d;
        if ((int) body.getPosition().x == points.get(points.size - 1).x && (int) body.getPosition().y == points.get(points.size - 1).y) {
            body.setLinearVelocity(0, 0);
        }

        if (points.get(index).x < (int) body.getPosition().x - (int) (previousDelta * 104 + 0.5) && previousDerection != Direction.RIGHT && player.changeIndex && !isChanged) {
            d = Direction.LEFT;
            body.setLinearVelocity(-104f, 0f);
            isChanged = true;

        }
        if (points.get(index).y > (int) body.getPosition().y + (int) (previousDelta * 104 + 0.5) && previousDerection != Direction.DOWN && player.changeIndex && !isChanged) {
            d = Direction.TOP;
            body.setLinearVelocity(0f, 104f);
            isChanged = true;
        }
        if (points.get(index).x > (int) body.getPosition().x + (int) (previousDelta * 104 + 0.5) && previousDerection != Direction.LEFT && player.changeIndex && !isChanged) {
            d = Direction.RIGHT;
            body.setLinearVelocity(104f, 0f);
            isChanged = true;
        }
        if (points.get(index).y < (int) body.getPosition().y - (int) (previousDelta * 104 + 0.5) && previousDerection != Direction.TOP && player.changeIndex && !isChanged) {
            d = Direction.DOWN;
            body.setLinearVelocity(0f, -104f);
        }

        previousDelta = deltaTime;
        return d;

    }

}
