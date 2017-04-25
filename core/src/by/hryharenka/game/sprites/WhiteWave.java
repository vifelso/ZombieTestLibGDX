package by.hryharenka.game.sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Andrei on 23.04.2017.
 */

public class WhiteWave extends Sprite {
    public Animation<TextureRegion> wave;
    private float stateTimer;
    private TextureRegion textureWave;

    public WhiteWave() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(new Texture("white_wave.png"), i * 66, 0, 66, 35));
        }
        wave = new Animation(0.25f, frames);
        frames.clear();
        textureWave = new TextureRegion(new Texture("white_wave.png"), 0, 0, 66, 35);
        setBounds(0, 0, 66, 35);
        setRegion(textureWave);
        stateTimer = 0;
    }

    public void update(float dt) {
        setRegion(getFrame(dt));

    }

    private TextureRegion getFrame(float dt) {
        TextureRegion region = wave.getKeyFrame(stateTimer, true);
        stateTimer = stateTimer + dt;
        return region;
    }
}
