package by.hryharenka.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Andrei on 24.04.2017.
 */

public class Hat extends Sprite {
    private java.lang.String name;
    public TextureRegion hatStandingUp;
    public TextureRegion hatStandingDown;
    public Animation<TextureRegion> hatWalkUp;
    public Animation<TextureRegion> hatWalkDown;
    public Animation<TextureRegion> hatCuttingDown;
    public Animation<TextureRegion> hatCuttingDownDouble;
    public Animation<TextureRegion> hatWalkDownDoubleWithWood;
    public Animation<TextureRegion> hatWalkDownWithWood;
    public Animation<TextureRegion> hatWalkUpWithWood;
    public Animation<TextureRegion> hatWalkUpDoubleWithWood;
    Array<TextureRegion> frames;
    TextureRegion flipped;

    public Hat() {
        name = "hat";
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 10; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_hat_walk_down.png"), i * 267, 0, 267, 200);
            frames.add(flipped);
        }
        hatWalkDown = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 9; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_hat_woodcut.png"), i * 267, 0, 267, 200);
            frames.add(flipped);
        }
        hatCuttingDown = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 10; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_hat_walk_up.png"), i * 267, 0, 267, 200);
            flipped.flip(true, false);
            frames.add(flipped);
        }
        hatWalkUp = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 9; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_double_hat_woodcut.png"), i * 267, 0, 267, 200);
            frames.add(flipped);
        }
        hatCuttingDownDouble = new Animation(0.1f, frames);
        frames.clear();


        for (int i = 0; i < 10; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_hat_walkwood_down.png"), i * 267, 0, 267, 200);
            frames.add(flipped);
        }
        hatWalkDownWithWood = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 10; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_double_hat_walkwood_down.png"), i * 267, 0, 267, 200);
            frames.add(flipped);
        }
        hatWalkDownDoubleWithWood = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 9; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_hat_woodcut.png"), i * 267, 0, 267, 200);
            frames.add(flipped);
        }
        hatCuttingDown = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 10; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_hat_walkwood_up.png"), i * 267, 0, 267, 200);
            flipped.flip(true, false);
            frames.add(flipped);
        }
        hatWalkUpWithWood = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 10; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_double_hat_walkwood_up.png"), i * 267, 0, 267, 200);
            flipped.flip(true, false);
            frames.add(flipped);
        }
        hatWalkUpDoubleWithWood = new Animation(0.1f, frames);
        frames.clear();

        setBounds(0, 0, 267, 200);
        hatStandingUp = new TextureRegion(new Texture("anim_woodcutter_hat_walk_up.png"), 0, 0, 267, 200);
        hatStandingUp.flip(true, false);
        hatStandingDown = new TextureRegion(new Texture("anim_woodcutter_hat_walk_down.png"), 0, 0, 267, 200);

    }


    public void changeHat(Player player) {
        if (player.clickButtonHat % 2 != 0) {
            name = "double_hat";
        } else name = "hat";


        for (int i = 0; i < 10; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_" + name + "_walk_down.png"), i * 267, 0, 267, 200);
            frames.add(flipped);
        }
        hatWalkDown = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 10; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_" + name + "_walk_up.png"), i * 267, 0, 267, 200);
            flipped.flip(true, false);
            frames.add(flipped);
        }
        hatWalkUp = new Animation(0.1f, frames);
        frames.clear();

        hatStandingUp = new TextureRegion(new Texture("anim_woodcutter_" + name + "_walk_up.png"), 0, 0, 267, 200);
        hatStandingUp.flip(true, false);
        hatStandingDown = new TextureRegion(new Texture("anim_woodcutter_" + name + "_walk_down.png"), 0, 0, 267, 200);
    }

}
