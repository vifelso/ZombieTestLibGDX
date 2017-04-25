package by.hryharenka.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Andrei on 24.04.2017.
 */

public class Cloth extends Sprite {
    private java.lang.String name;
    public TextureRegion clothStandingUp;
    public TextureRegion clothStandingDown;
    public Animation<TextureRegion> clothWalkUp;
    public Animation<TextureRegion> clothWalkDown;
    public Animation<TextureRegion> clothCuttingDownDouble;
    public Animation<TextureRegion> clothCuttingDown;
    public Animation<TextureRegion> clothWalkDownWithWood;
    public Animation<TextureRegion> doubleClothWalkDownWithWood;
    public Animation<TextureRegion> clothWalkUpWithWood;
    public Animation<TextureRegion> doubleClothWalkUpWithWood;
    Array<TextureRegion> frames;
    TextureRegion flipped;


    public Cloth() {
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 10; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_cloth_walk_down.png"), i * 267, 0, 267, 200);
            frames.add(flipped);
        }
        clothWalkDown = new Animation(0.1f, frames);
        frames.clear();


        for (int i = 0; i < 9; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_cloth_woodcut.png"), i * 267, 0, 267, 200);
            frames.add(flipped);
        }
        clothCuttingDown = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 10; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_cloth_walk_up.png"), i * 267, 0, 267, 200);
            flipped.flip(true, false);
            frames.add(flipped);
        }
        clothWalkUp = new Animation(0.1f, frames);
        frames.clear();


        for (int i = 0; i < 9; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_double_cloth_woodcut.png"), i * 267, 0, 267, 200);
            frames.add(flipped);
        }
        clothCuttingDownDouble = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 9; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_cloth_woodcut.png"), i * 267, 0, 267, 200);
            frames.add(flipped);
        }
        clothCuttingDown = new Animation(0.1f, frames);
        frames.clear();


        for (int i = 0; i < 10; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_cloth_walkwood_down.png"), i * 267, 0, 267, 200);
            frames.add(flipped);
        }
        clothWalkDownWithWood = new Animation(0.1f, frames);
        frames.clear();


        for (int i = 0; i < 10; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_double_cloth_walkwood_down.png"), i * 267, 0, 267, 200);
            frames.add(flipped);
        }
        doubleClothWalkDownWithWood = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 10; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_cloth_walkwood_up.png"), i * 267, 0, 267, 200);
            flipped.flip(true, false);
            frames.add(flipped);
        }
        clothWalkUpWithWood = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 10; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_double_cloth_walkwood_up.png"), i * 267, 0, 267, 200);
            flipped.flip(true, false);
            frames.add(flipped);
        }
        doubleClothWalkUpWithWood = new Animation(0.1f, frames);
        frames.clear();
        setBounds(0, 0, 267, 200);
        clothStandingUp = new TextureRegion(new Texture("anim_woodcutter_cloth_walk_up.png"), 0, 0, 267, 200);
        clothStandingUp.flip(true, false);
        clothStandingDown = new TextureRegion(new Texture("anim_woodcutter_cloth_walk_down.png"), 0, 0, 267, 200);
    }

    public void changeCloth(Player player) {
        if (player.clickButtonCloth % 2 != 0) {
            name = "double_cloth";
        } else name = "cloth";
        for (int i = 0; i < 10; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_" + name + "_walk_down.png"), i * 267, 0, 267, 200);
            frames.add(flipped);
        }
        clothWalkDown = new Animation(0.1f, frames);
        frames.clear();
        for (int i = 0; i < 10; i++) {
            flipped = new TextureRegion(new Texture("anim_woodcutter_" + name + "_walk_up.png"), i * 267, 0, 267, 200);
            flipped.flip(true, false);
            frames.add(flipped);
        }
        clothWalkUp = new Animation(0.1f, frames);
        frames.clear();
        clothStandingUp = new TextureRegion(new Texture("anim_woodcutter_" + name + "_walk_up.png"), 0, 0, 267, 200);
        clothStandingUp.flip(true, false);
        clothStandingDown = new TextureRegion(new Texture("anim_woodcutter_" + name + "_walk_down.png"), 0, 0, 267, 200);

    }

}
