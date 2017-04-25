package by.hryharenka.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import by.hryharenka.game.ZombieGame;
import by.hryharenka.game.sprites.AiZombie;
import by.hryharenka.game.sprites.Cloth;
import by.hryharenka.game.sprites.Hat;
import by.hryharenka.game.sprites.Player;
import by.hryharenka.game.sprites.WhiteWave;
import by.hryharenka.game.tools.B2WorldCreator;

/**
 * Created by Andrei on 19.04.2017.
 */

public class PlayScreen implements Screen {
    private Skin buttonSkin;
    private TextureAtlas buttonsAtlas;
    private Stage stage;
    private TextButton buttonHat;
    private TextButton buttonCloth;
    TextButton.TextButtonStyle textButtonStyle;
    private ZombieGame game;
    Vector2 dragOld, dragNew;
    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;


    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //sprites
    private Player player;
    private Hat hat;
    private Cloth cloth;
    private AiZombie aiZombie1;
    private AiZombie aiZombie2;
    private AiZombie aiZombie3;

    public PlayScreen(ZombieGame game) {
        this.game = game;
        //create cam used to follow mario through cam world
        gamecam = new OrthographicCamera();
        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(ZombieGame.V_WIDTH, ZombieGame.V_HEIGHT, gamecam);
        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("zombieMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1);
        //initially set our gamcam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        //create our Box2D world, setting no gravity in X
        world = new World(new Vector2(0, 0), true);
        //allows for debug lines of our box2d world.
        //b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);
        System.out.println("creating hat");
        hat = new Hat();
        System.out.println("created hat");
        System.out.println("creating cloth");
        cloth = new Cloth();
        System.out.println("created hat");
        System.out.println("creating player");
        player = new Player(world, map, hat, cloth, 52, 52);
        System.out.println("created player");
        aiZombie1 = new AiZombie(world, map, 364, 364, 364, 364, hat, cloth);
        aiZombie2 = new AiZombie(world, map, 572, 780, 572, 580, hat, cloth);
        aiZombie3 = new AiZombie(world, map, 780, 260, 780, 260, hat, cloth);
        stage = new Stage();        // window is stage *//*
        stage.clear();
        Gdx.input.setInputProcessor(stage); // stage is responsive *//*
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        buttonsAtlas = new TextureAtlas("buttonYellow.pack"); /*button atlas image */
        buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas);
        textButtonStyle.up = buttonSkin.getDrawable("buttonYellow");
        textButtonStyle.down = buttonSkin.getDrawable("buttonYellow");
        buttonHat = new TextButton("CHANGE HAT", textButtonStyle);
        buttonCloth = new TextButton("CHANGE CLOTH", textButtonStyle);
        buttonCloth.setPosition(200, 0);
        stage.addActor(buttonHat);
        stage.addActor(buttonCloth);
        buttonHat.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                player.changeHat();
                player.clickButtonHat++;
                System.out.println("buttonHat");
            }
        });
        buttonCloth.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                player.changeCloth();
                player.clickButtonCloth++;
                System.out.println("buttonCloth");
            }
        });

    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt) {

        if (Gdx.input.justTouched()) {
            dragNew = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            dragOld = dragNew;
        }
       /* if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {

           touch(dragNew.x,dragNew.y);
           // System.out.println(dragNew);
        }*/


        //scrolling
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            dragNew = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            if (!dragNew.equals(dragOld) && (!cameraOutOfLimit(gamecam.position))) {
                gamecam.translate(dragOld.x - dragNew.x, dragNew.y - dragOld.y);//Translate by subtracting the vectors
                if (gamecam.position.x < 300) {
                    gamecam.position.x = 300;
                }
                if (gamecam.position.x > 728) {
                    gamecam.position.x = 728;
                }
                if (gamecam.position.y < 300) {
                    gamecam.position.y = 300;
                }
                if (gamecam.position.y > 728) {
                    gamecam.position.y = 728;
                }
                gamecam.update();
                dragOld = dragNew;

            }
        }


    }

    private boolean cameraOutOfLimit(Vector3 position) {
        boolean result = false;
        boolean isOut = false;
        float x_left_limit = 300;
        float x_right_limit = 728;
        float y_bottom_limit = 300;
        float y_top_limit = 728;
        if (position.x < x_left_limit) {
            isOut = true;
            position.x = 300;
        }
        if (position.x > x_right_limit) {
            isOut = true;
            position.x = 728;
        }
        if (position.y < y_bottom_limit) {
            isOut = true;
            position.y = 300;
        }
        if (position.y > y_top_limit) {
            isOut = true;
            position.y = 728;
        }
        if (isOut) {
            result = true;
        }
        return result;
    }

    //just move to define point
    public void touch(float screenX, float screenY) {

        // calculte the normalized direction from the body to the touch position
       /* Vector2 direction = new Vector2(screenX, screenY);
        direction.sub(player.body.getPosition());
        direction.nor();
        float speed = 50;
        player.body.setLinearVelocity(direction.scl(speed));*/

    }

    private void update(float dt) {
        gamecam.update();
        handleInput(dt);
        world.step(1 / 60f, 6, 6);
        player.update(dt, map, gamecam);
        aiZombie1.updateAi(dt, map, gamecam);
        aiZombie2.updateAi(dt, map, gamecam);
        aiZombie3.updateAi(dt, map, gamecam);
    }

    @Override
    public void render(float delta) {
        //update our gamecam with correct coordinates after changes
        update(delta);
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(gamecam);
        renderer.render();

        //renderer our Box2DDebugLines
        //b2dr.render(world, gamecam.combined);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        if (player.whiteWaves != null && player.whiteWaves.size > 0) {
            for (WhiteWave whiteWave : player.whiteWaves) {
                whiteWave.update(delta);
                whiteWave.draw(game.batch);
            }
        }
        player.draw(game.batch);
        player.spriteHat.draw(game.batch);
        player.spriteCloth.draw(game.batch);
        aiZombie1.draw(game.batch);
        aiZombie2.draw(game.batch);
        aiZombie3.draw(game.batch);
        game.batch.end();
        stage.draw();
    }



/*    public void showWhiteWave(){
        if(player.paths != null){
            Array<WhiteWave> whiteWaves = new Array<>();
            for(int currentTile = index; currentTile < player.paths.size; currentTile++){
                WhiteWave whiteWave = new WhiteWave();
                whiteWave.setPosition(player.paths.get(currentTile).x,player.paths.get(currentTile).y);
            }
            waves = whiteWaves;
        }


    }*/

    @Override
    public void resize(int width, int height) {
        //updated our game viewport
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
