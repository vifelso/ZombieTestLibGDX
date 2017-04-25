package by.hryharenka.game.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import by.hryharenka.game.pathfinding.RunnerPathFinding;
import by.hryharenka.game.tools.DirectionAnVelocitySetter;

/**
 * Created by Andrei on 23.04.2017.
 */

public class AiZombie extends Player {

    float timerForAiZombie;
    boolean inactivity;


    public AiZombie(World world, TiledMap map, int positionTextureX, int positionTextureY, int positionBodyX, int positionBodyY, Hat hat, Cloth cloth) {
        super(world, map, hat, cloth, positionBodyX, positionBodyY);
        setPosition(positionTextureX, positionTextureY);
        System.out.println("Height : " + positionTextureX);
        System.out.println("Widht : " + positionTextureY);
        timerForAiZombie = 25;

    }


    public void updateAi(float dt, TiledMap map, OrthographicCamera gamecam) {
        if (body.getPosition().x >= 988) {
            body.setTransform(988, body.getPosition().y, 0);
            body.setLinearVelocity(0, 0);


        }
        if (body.getPosition().x < 52) {
            body.setTransform(52, body.getPosition().y, 0);
            body.setLinearVelocity(0, 0);

        }
        if (body.getPosition().y < 52) {
            body.setTransform(body.getPosition().x, 52, 0);
            body.setLinearVelocity(0, 0);

        }
        if (body.getPosition().y >= 988) {
            body.setTransform(body.getPosition().x, 988, 0);
            body.setLinearVelocity(0, 0);

        }
        if (isCutting && timerCutting < 5) {
            timerCutting += dt;
            //currentState = State.CUTTING_DOWN;
        } else if (isCutting && timerCutting > 5) {
            isCutting = false;
            timerCutting = 0;
            isReverse = true;
            x = paths.get(paths.size - 1).x;
            y = paths.get(paths.size - 1).y;
            //previousState = State.CUTTING_DOWN;
        }
        if (timerForAiZombie > 25) {
            Random rn = new Random();
            int randomIndexX = ThreadLocalRandom.current().nextInt(0, 9 + 1);
            int randomIndexY = ThreadLocalRandom.current().nextInt(0, 9 + 1);
            System.out.println("randoX :" + randomIndexX);
            System.out.println("randoY :" + randomIndexY);
            int simulateClickX = randomIndexX * 104 + 52;
            int simulateClickY = randomIndexY * 104 + 52;


            if (!isContinue && !isCutting && !isReverse) {

                int inputX = (int) simulateClickX;
                int inputY = (int) simulateClickY;
                x = (int) inputX;
                y = (int) inputY;


                if (!isCutting && !isReverse && trees.contains(new Vector2(x, y), false)) {
                    System.out.println("pathTrees");

                    //MapManager.loadLevelTrees(map);
                    inputX += 104;
                    isTree = true;
                    //isReverse = false;

                    runnerPathFinding = new RunnerPathFinding();
                    paths = runnerPathFinding.callPathFinding(body, inputX, inputY, this);
                    isJustCreated = true;


                    if (paths.size <= 1) {
                        paths = null;
                        index = 0;
                        body.setLinearVelocity(0, 0);
                        isContinue = false;
                        //body.setTransform(x, y, 0);
                        isJustCreated = false;
                        whiteWaves = null;
                    } else {
                        directionAnVelocitySetter = new DirectionAnVelocitySetter(body, paths, dt, 0);
                        changeIndex = true;
                        //index++;

                    }


                } else if (buildings.contains(new Vector2(x, y), false)) {
                    System.out.println("pathBulding");
                } else {

                    //MapManager.loadLevel(map);
                    runnerPathFinding = new RunnerPathFinding();
                    if (isReverse && paths != null) {
                        paths = runnerPathFinding.callPathFinding(body, (int) paths.get(paths.size - 1).x, (int) paths.get(paths.size - 1).y, this);
                    } else paths = runnerPathFinding.callPathFinding(body, inputX, inputY, this);

                    isJustCreated = true;


                    directionAnVelocitySetter = new DirectionAnVelocitySetter(body, paths, dt, 0);
                    changeIndex = true;
                    index++;


                }
            }


            timerForAiZombie = 0;
        } else {
            timerForAiZombie += dt;
            System.out.println();
        }

        if (paths != null && index < paths.size && !isTree && !isCutting) {
            s += dt * 104;
            if (104 > s) {
                directionAnVelocitySetter.setDirectionAndVelocity(index, body, s, this, dt);
                changeIndex = false;
                isContinue = true;
                //  showWhiteWave();
                isJustCreated = false;

            } else {
                index++;
                startTime = 0;
                s = 0;
                changeIndex = true;
                isContinue = true;

                isJustCreated = false;
                if (index < paths.size) {
                    body.setTransform(paths.get(index - 1).x, paths.get(index - 1).y, 0);
                }

            }

            if (index == paths.size) {
                if (isReverse) {
                    isReverse = false;
                }
                body.setTransform(paths.get(index - 1).x, paths.get(index - 1).y, 0);
                index = 0;
                body.setLinearVelocity(0, 0);
                isContinue = false;


                isJustCreated = false;
                whiteWaves = null;
                paths = null;
            }

        }

        if (paths != null && index < paths.size && isTree) {
            s += dt * 104;
            if (104 > s) {
                directionAnVelocitySetter.setDirectionAndVelocity(index, body, s, this, dt);
                changeIndex = false;
                isContinue = true;
                //  showWhiteWave();
                isJustCreated = false;

            } else {
                index++;
                startTime = 0;
                s = 0;
                changeIndex = true;
                isContinue = true;
                //showWhiteWave();
                isJustCreated = false;
                if (index < paths.size) {
                    body.setTransform(paths.get(index - 1).x, paths.get(index - 1).y, 0);
                }

            }

            if (index == paths.size) {
                body.setLinearVelocity(0, 0);
                paths.reverse();
                index = 0;
                //body.setLinearVelocity(0, 0);
                isTree = false;
                // isReverse = true;
                isCutting = true;
            }

        }
        setRegion(getFrame(dt));
        setPosition((body.getPosition().x - getWidth() / 2), (body.getPosition().y - getHeight() / 2) - 20);

    }


    @Override
    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region = null;
        switch (currentState) {
            case WALKING_UP:
                region = zombieWalkingUp.getKeyFrame(stateTimer, true);
                break;
            case WALKING_DOWN:
                region = zombieWalkingDown.getKeyFrame(stateTimer, true);
                break;
            case CUTTING_UP:
                break;
            case CUTTING_DOWN:
                region = zombieCuttingDown.getKeyFrame(stateTimer, true);

                break;
            case STANDING_UP:
                region = zombieStandingUp;
                break;
            case STANDING_DOWN:
                region = zombieStandingDown;

                break;
            case WALKING_DOWN_WITH_WOOD:
                region = zombieWalkingDownWithWood.getKeyFrame(stateTimer, true);
                break;
            case WALKING_UP_WITH_WOOD:
                region = zombieWalkingUpWithWood.getKeyFrame(stateTimer, true);
                break;
            default:
                region = zombieStandingDown;
                break;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if ((body.getLinearVelocity().y > 0 || body.getLinearVelocity().x > 0) && !isReverse) {
            return State.WALKING_UP;
        } else if ((body.getLinearVelocity().y < 0 || body.getLinearVelocity().x < 0) && !isReverse) {
            return State.WALKING_DOWN;
        } else if ((body.getLinearVelocity().x == 0 && body.getLinearVelocity().x == 0) && !isCutting && !isReverse) {
            return State.STANDING_DOWN;
        } else if ((body.getLinearVelocity().x == 0 && body.getLinearVelocity().x == 0) && isCutting) {
            return State.CUTTING_DOWN;
        } else if ((body.getLinearVelocity().y > 0 || body.getLinearVelocity().x > 0) && isReverse) {
            return State.WALKING_UP_WITH_WOOD;
        } else if ((body.getLinearVelocity().y < 0 || body.getLinearVelocity().x < 0) && isReverse) {
            return State.WALKING_DOWN_WITH_WOOD;
        } else return State.STANDING_UP;
    }


}
