package by.hryharenka.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import by.hryharenka.game.managers.MapManager;
import by.hryharenka.game.pathfinding.RunnerPathFinding;
import by.hryharenka.game.tools.DirectionAnVelocitySetter;


/**
 * Created by Andrei on 19.04.2017.
 */

public class Player extends Sprite {


    public void changeHat() {
        isHatChanged = !isHatChanged;
    }

    public void changeCloth() {
        isClothChanged = !isClothChanged;
    }

    //states
    public enum State {
        STANDING_UP, STANDING_DOWN, WALKING_UP, WALKING_DOWN, CUTTING_UP, CUTTING_DOWN, WALKING_UP_WITH_WOOD, WALKING_DOWN_WITH_WOOD
    }

    public float timerCutting = 0;
    public boolean isCutting;
    public boolean isReverse;
    public boolean isTree;
    public State currentState;
    public State previousState;
    public float stateTimer;
    public TextureRegion zombieStandingUp;
    public TextureRegion zombieStandingDown;

    //public Animation<TextureRegion> zombieCuttingUp;
    public Animation<TextureRegion> zombieCuttingDown;

    public Animation<TextureRegion> zombieWalkingUp;
    public Animation<TextureRegion> zombieWalkingDown;

    public Animation<TextureRegion> zombieWalkingUpWithWood;
    public Animation<TextureRegion> zombieWalkingDownWithWood;


    public Array<Vector2> trees;
    public Array<Vector2> buildings;
    public int clickButtonCloth = 0;
    public int clickButtonHat = 0;
    public boolean isHatChanged = false;
    public boolean isClothChanged = false;
    public Hat spriteHat;
    public Cloth spriteCloth;
    //world
    public World world;
    public Body body;

    public Array<WhiteWave> whiteWaves;

    public TiledMap map;
    public boolean isContinue = false;
    public boolean changeIndex = false;
    public float s;
    public int index = 0;

    public boolean isJustCreated = false;

    public long startTime = 0;

    public float x;
    public float y;

    public Array<Vector2> paths;
    public RunnerPathFinding runnerPathFinding;

    public DirectionAnVelocitySetter directionAnVelocitySetter;

    public Player(World world, TiledMap map, Hat hat, Cloth cloth, float bodyX, float bodyY) {
        this.world = world;
        setTexture(new Texture("anim_woodcutter_stand.png"));
        definePlayer(bodyX, bodyY);
        setBounds(0, 0, 267, 200);
        setRegion(getTexture());
        setPosition(52, 52);
        this.map = map;
        MapManager.loadLevel(map);
        MapManager.loadLevelTrees(map);
        trees = MapManager.trees;
        buildings = MapManager.buildings;
        currentState = State.STANDING_DOWN;
        previousState = State.STANDING_DOWN;
        stateTimer = 0;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        System.out.println("next");
        for (int i = 0; i < 10; i++) {
            TextureRegion flipped = new TextureRegion(new Texture("anim_woodcutter_walk_up.png"), i * 267, 0, 267, 200);
            flipped.flip(true, false);
            frames.add(flipped);
        }
        zombieWalkingUp = new Animation(0.1f, frames);
        frames.clear();

        System.out.println("next");


        for (int i = 0; i < 10; i++) {
            TextureRegion flipped = new TextureRegion(new Texture("anim_woodcutter_walkwood_down.png"), i * 267, 0, 267, 200);
            frames.add(flipped);
        }
        zombieWalkingDownWithWood = new Animation(0.1f, frames);
        frames.clear();
        System.out.println("next");
        for (int i = 0; i < 10; i++) {
            TextureRegion flipped = new TextureRegion(new Texture("anim_woodcutter_walkwood_up.png"), i * 267, 0, 267, 200);
            flipped.flip(true, false);
            frames.add(flipped);
        }
        zombieWalkingUpWithWood = new Animation(0.1f, frames);
        frames.clear();
        System.out.println("next");
        for (int i = 0; i < 9; i++) {
            TextureRegion flipped = new TextureRegion(new Texture("anim_woodcutter_woodcut.png"), i * 267, 0, 267, 200);
            frames.add(flipped);
        }
        zombieCuttingDown = new Animation(0.1f, frames);
        frames.clear();
        System.out.println("next");
        for (int i = 0; i < 10; i++) {
            frames.add(new TextureRegion(new Texture("anim_woodcutter_walk_down.png"), i * 267, 0, 267, 200));
        }
        zombieWalkingDown = new Animation(0.1f, frames);
        frames.clear();
        System.out.println("next");
        zombieStandingUp = new TextureRegion(new Texture("anim_woodcutter_walk_up.png"), 0, 0, 267, 200);
        zombieStandingUp.flip(true, false);
        zombieStandingDown = new TextureRegion(new Texture("anim_woodcutter_walk_down.png"), 0, 0, 267, 200);
        setRegion(zombieStandingDown);
        System.out.println("nextEntity");
        spriteHat = hat;
        spriteCloth = cloth;
        System.out.println("end next");


    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region = null;
        switch (currentState) {
            case WALKING_UP:
                region = zombieWalkingUp.getKeyFrame(stateTimer, true);
                spriteHat.setRegion(spriteHat.hatWalkUp.getKeyFrame(stateTimer, true));
                spriteCloth.setRegion(spriteCloth.clothWalkUp.getKeyFrame(stateTimer, true));
                break;
            case WALKING_DOWN:
                region = zombieWalkingDown.getKeyFrame(stateTimer, true);
                spriteHat.setRegion(spriteHat.hatWalkDown.getKeyFrame(stateTimer, true));
                spriteCloth.setRegion(spriteCloth.clothWalkDown.getKeyFrame(stateTimer, true));
                break;
            case CUTTING_UP:
                break;
            case CUTTING_DOWN:
                region = zombieCuttingDown.getKeyFrame(stateTimer, true);

                if (clickButtonHat % 2 == 0) {
                    spriteHat.setRegion(spriteHat.hatCuttingDown.getKeyFrame(stateTimer, true));

                } else
                    spriteHat.setRegion(spriteHat.hatCuttingDownDouble.getKeyFrame(stateTimer, true));

                if (clickButtonCloth % 2 == 0) {
                    spriteCloth.setRegion(spriteCloth.clothCuttingDown.getKeyFrame(stateTimer, true));
                } else
                    spriteCloth.setRegion(spriteCloth.clothCuttingDownDouble.getKeyFrame(stateTimer, true));
                break;
            case STANDING_UP:
                region = zombieStandingUp;
                spriteHat.setRegion(spriteHat.hatStandingUp);
                spriteCloth.setRegion(spriteCloth.clothStandingUp);
                break;
            case STANDING_DOWN:
                region = zombieStandingDown;
                spriteHat.setRegion(spriteHat.hatStandingDown);
                spriteCloth.setRegion(spriteCloth.clothStandingDown);
                break;
            case WALKING_DOWN_WITH_WOOD:
                region = zombieWalkingDownWithWood.getKeyFrame(stateTimer, true);
                if (clickButtonHat % 2 == 0) {
                    spriteHat.setRegion(spriteHat.hatWalkDownWithWood.getKeyFrame(stateTimer, true));
                } else
                    spriteHat.setRegion(spriteHat.hatWalkDownDoubleWithWood.getKeyFrame(stateTimer, true));
                if (clickButtonCloth % 2 == 0) {
                    spriteCloth.setRegion(spriteCloth.clothWalkDownWithWood.getKeyFrame(stateTimer, true));
                } else
                    spriteCloth.setRegion(spriteCloth.doubleClothWalkDownWithWood.getKeyFrame(stateTimer, true));

                break;
            case WALKING_UP_WITH_WOOD:
                region = zombieWalkingUpWithWood.getKeyFrame(stateTimer, true);
                if (clickButtonHat % 2 == 0) {
                    spriteHat.setRegion(spriteHat.hatWalkUpWithWood.getKeyFrame(stateTimer, true));
                } else
                    spriteHat.setRegion(spriteHat.hatWalkUpDoubleWithWood.getKeyFrame(stateTimer, true));
                if (clickButtonCloth % 2 == 0) {
                    spriteCloth.setRegion(spriteCloth.clothWalkUpWithWood.getKeyFrame(stateTimer, true));
                } else
                    spriteCloth.setRegion(spriteCloth.doubleClothWalkUpWithWood.getKeyFrame(stateTimer, true));

                break;
            default:
                region = zombieStandingDown;
                spriteHat.setRegion(spriteHat.hatStandingDown);
                spriteCloth.setRegion(spriteCloth.clothStandingDown);
                break;
        }


        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if ((body.getLinearVelocity().y > 0 || body.getLinearVelocity().x > 0) && !isReverse && !isCutting) {
            return State.WALKING_UP;
        } else if ((body.getLinearVelocity().y < 0 || body.getLinearVelocity().x < 0) && !isReverse && !isCutting) {
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

    public Player() {
    }

    public void update(float dt, TiledMap map, OrthographicCamera gamecam) {
        if (body.getPosition().x >= 1040) {
            body.setTransform(988, body.getPosition().y, 0);
            body.setLinearVelocity(0, 0);


        }
        if (body.getPosition().x < 0) {
            body.setTransform(52, body.getPosition().y, 0);
            body.setLinearVelocity(0, 0);

        }
        if (body.getPosition().y < 0) {
            body.setTransform(body.getPosition().x, 52, 0);
            body.setLinearVelocity(0, 0);

        }
        if (body.getPosition().y >= 1040) {
            body.setTransform(body.getPosition().x, 988, 0);
            body.setLinearVelocity(0, 0);

        }
        if (isCutting && timerCutting <= 5) {
            timerCutting += dt;
        } else if (isCutting && timerCutting > 5) {
            isCutting = false;
            timerCutting = 0;
            isReverse = true;
            body.setLinearVelocity(0, 0);
            x = paths.get(paths.size - 1).x;
            y = paths.get(paths.size - 1).y;
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            Vector3 vector3 = getMousePosInGameWorld(gamecam);

            if (!isContinue && !isCutting && !isReverse) {
                System.out.println(vector3.x);
                System.out.println(vector3.y);
                int inputX = (int) vector3.x;
                int inputY = (int) vector3.y;
                x = ((int) inputX / 104) * 104 + 52;
                y = ((int) inputY / 104) * 104 + 52;


                if (!isCutting && !isReverse && trees.contains(new Vector2(x, y), false)) {
                    inputX += 104;
                    isTree = true;
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
                        whiteWaves = new Array<>();
                        for (int i = 0; i < paths.size; i++) {
                            WhiteWave whiteWave = new WhiteWave();
                            whiteWave.setPosition(paths.get(i).x - whiteWave.getWidth() / 2, paths.get(i).y - whiteWave.getHeight() / 2 - 35);
                            whiteWaves.add(whiteWave);
                        }
                    }


                } else if (buildings.contains(new Vector2(x, y), false)) {
                    System.out.println("pathBulding");
                } else {
                    runnerPathFinding = new RunnerPathFinding();
                    if (isReverse && paths != null) {
                        paths = runnerPathFinding.callPathFinding(body, (int) paths.get(paths.size - 1).x, (int) paths.get(paths.size - 1).y, this);
                    } else paths = runnerPathFinding.callPathFinding(body, inputX, inputY, this);
                    isJustCreated = true;
                    directionAnVelocitySetter = new DirectionAnVelocitySetter(body, paths, dt, 0);
                    changeIndex = true;
                    index++;
                    whiteWaves = new Array<>();
                    for (int i = 0; i < paths.size; i++) {
                        WhiteWave whiteWave = new WhiteWave();
                        whiteWave.setPosition(paths.get(i).x - whiteWave.getWidth() / 2, paths.get(i).y - whiteWave.getHeight() / 2 - 35);
                        whiteWaves.add(whiteWave);
                    }

                }
            }


        }
        if (paths != null && index < paths.size && !isTree && !isCutting) {

            s += dt * 104;
            if (104 > s) {
                directionAnVelocitySetter.setDirectionAndVelocity(index, body, s, this, dt);
                changeIndex = false;
                isContinue = true;
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
                s = 0;
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
                s = 0;
                body.setLinearVelocity(0, 0);
                paths.reverse();
                index = 0;
                //body.setLinearVelocity(0, 0);
                isTree = false;
                isCutting = true;
            }

        }


        if (isHatChanged) {
            spriteHat.changeHat(this);
            isHatChanged = !isHatChanged;
        }

        if (isClothChanged) {
            spriteCloth.changeCloth(this);
            isClothChanged = !isClothChanged;
        }

        setRegion(getFrame(dt));
        setPosition((body.getPosition().x - getWidth() / 2), (body.getPosition().y - getHeight() / 2) - 20);

        spriteHat.setPosition((body.getPosition().x - getWidth() / 2), (body.getPosition().y - getHeight() / 2) - 20);
        spriteCloth.setPosition((body.getPosition().x - getWidth() / 2), (body.getPosition().y - getHeight() / 2) - 20);
    }


    public void definePlayer(float bodyX, float bodyY) {
        BodyDef bDef = new BodyDef();
        bDef.position.set(bodyX, bodyY);
        bDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bDef);
        FixtureDef fDef = new FixtureDef();
        CircleShape boxShape = new CircleShape();
        boxShape.setRadius(45);
        fDef.shape = boxShape;
        body.createFixture(fDef);
    }

    public Vector3 getMousePosInGameWorld(OrthographicCamera camera) {
        return camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }
}
